#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
读取并还原 Redis 中的登录图形验证码（开发/调试辅助脚本，纯标准库，无第三方依赖）。

后端生成验证码时，会把验证码明文小写后的 SHA-256 hex 存入 Redis key `captcha:{id}`
（TTL 约 5 分钟），明文本身不落库。本脚本直连 Redis 列出所有 `captcha:*` key，
并用穷举 32^4 组合（约 1 秒）还原出 4 位验证码明文，配合浏览器自动化登录使用。

用法:
    python scripts/read_captcha.py [选项]

选项:
    --host HOST      Redis 地址（默认 127.0.0.1）
    --port PORT      Redis 端口（默认 6379）
    --password PWD   Redis 密码（默认 123456；传 - 表示无密码）
    --db N           Redis 库编号（默认 0）
    --new            先调用后端 /api/v1/{realm}/captcha 生成一个新验证码，再读取还原
    --realm X        --new 时使用的端：admin | portal（默认 admin）
    --backend URL    后端地址（默认 http://127.0.0.1:8000）

输出: 每行一个 `captchaId<TAB>验证码`，便于脚本解析。
"""

import argparse
import hashlib
import itertools
import json
import re
import socket
import sys
import urllib.request

# 与后端 AuthCryptoServiceImpl.CAPTCHA_ALPHABET 一致（小写形式，验证时后端也会转小写再比对）
DEFAULT_ALPHABET = "23456789abcdefghjklmnpqrstuvwxyz"


class Redis:
    """极简 RESP2 客户端（AUTH / SELECT / KEYS / GET 够用）。"""

    def __init__(self, host, port, password, db):
        self.sock = socket.create_connection((host, port), timeout=10)
        self.buf = b""
        if password and password != "-":
            self._cmd("AUTH", password)
        if db:
            self._cmd("SELECT", str(db))

    def _recv_more(self):
        chunk = self.sock.recv(65536)
        if not chunk:
            raise ConnectionError("redis 连接已断开")
        self.buf += chunk

    def _read_line(self):
        while b"\r\n" not in self.buf:
            self._recv_more()
        line, self.buf = self.buf.split(b"\r\n", 1)
        return line

    def _read_exact(self, n):
        while len(self.buf) < n + 2:
            self._recv_more()
        data = self.buf[:n]
        self.buf = self.buf[n + 2:]
        return data

    def _read_reply(self):
        while not self.buf:
            self._recv_more()
        prefix, self.buf = self.buf[:1], self.buf[1:]
        if prefix == b"+":
            return self._read_line().decode()
        if prefix == b"-":
            raise RuntimeError(self._read_line().decode())
        if prefix == b":":
            return int(self._read_line())
        if prefix == b"$":
            n = int(self._read_line())
            if n == -1:
                return None
            return self._read_exact(n)
        if prefix == b"*":
            n = int(self._read_line())
            if n == -1:
                return None
            return [self._read_reply() for _ in range(n)]
        raise RuntimeError("无法解析 redis 返回: %r" % prefix)

    def _cmd(self, *args):
        payload = b"*%d\r\n" % len(args)
        for arg in args:
            b = arg if isinstance(arg, bytes) else str(arg).encode()
            payload += b"$%d\r\n%s\r\n" % (len(b), b)
        self.sock.sendall(payload)
        return self._read_reply()

    def close(self):
        try:
            self.sock.close()
        except OSError:
            pass


def crack(hashed, alphabet):
    """穷举还原 4 位验证码明文；失败返回 None。

    Redis 里的值是 Redisson 序列化后的字节（本地默认带 \\x03\\xc1\\x01 前缀），
    其中嵌有 64 位 SHA-256 hex，用正则直接提取，兼容有无前缀。
    """
    raw = hashed if isinstance(hashed, bytes) else str(hashed or "").encode()
    m = re.search(rb"[0-9a-fA-F]{64}", raw)
    if not m:
        return None
    target = m.group(0).decode().lower()
    alpha = alphabet.encode()
    for combo in itertools.product(alpha, repeat=4):
        if hashlib.sha256(bytes(combo)).hexdigest() == target:
            return bytes(combo).decode()
    return None


def fetch_new_captcha(realm, backend):
    """调用后端生成新验证码，返回 captchaId。"""
    url = "%s/api/v1/%s/captcha" % (backend.rstrip("/"), realm)
    with urllib.request.urlopen(url, timeout=10) as resp:
        payload = json.loads(resp.read().decode() or "{}")
    data = payload.get("data") or {}
    cid = data.get("captcha_id") or data.get("captchaId")
    if not cid:
        raise RuntimeError("后端未返回 captchaId: %s" % payload)
    return cid


def main():
    parser = argparse.ArgumentParser(description="读取并还原 Redis 中的登录图形验证码")
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, default=6379)
    parser.add_argument("--password", default="123456", help="Redis 密码，- 表示无密码")
    parser.add_argument("--db", type=int, default=0)
    parser.add_argument("--new", action="store_true", help="先从后端生成新验证码再读取")
    parser.add_argument("--realm", choices=["admin", "portal"], default="admin")
    parser.add_argument("--backend", default="http://127.0.0.1:8000")
    parser.add_argument("--alphabet", default=DEFAULT_ALPHABET)
    args = parser.parse_args()

    redis = Redis(args.host, args.port, args.password, args.db)
    try:
        wanted = None
        if args.new:
            wanted = fetch_new_captcha(args.realm, args.backend)
            print("已生成新验证码 id: %s" % wanted, file=sys.stderr)
            # 等待 key 落库（后端异步写入一般即时，重试几次兜底）
            for _ in range(5):
                keys = redis._cmd("KEYS", "captcha:%s" % wanted)
                if keys:
                    break
                time_sleep(0.2)
        else:
            keys = redis._cmd("KEYS", "captcha:*")

        if not keys:
            print("Redis 中没有 captcha:* 的 key（可用 --new 先生成一个）", file=sys.stderr)
            return 1

        results = []
        for key in keys:
            hashed = redis._cmd("GET", key)
            ttl = redis._cmd("TTL", key) or 0
            code = crack(hashed, args.alphabet)
            cid = key.decode() if isinstance(key, bytes) else str(key)
            cid = cid[len("captcha:"):]
            results.append((int(ttl), cid, code))

        # 按剩余 TTL 降序：最新生成的验证码排在最前
        results.sort(key=lambda r: r[0], reverse=True)
        for ttl, cid, code in results:
            if code is None:
                print("%s\t<无法还原>\tTTL %ds" % (cid, ttl), file=sys.stderr)
            else:
                print("%s\t%s\tTTL %ds" % (cid, code, ttl))
        return 0
    finally:
        redis.close()


def time_sleep(seconds):
    import time
    time.sleep(seconds)


if __name__ == "__main__":
    sys.exit(main())
