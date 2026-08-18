#!/usr/bin/env python3
"""Convert scripts/db.sql (PostgreSQL dump) into postgresql (no jsonb) and mysql variants."""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "scripts" / "db.sql"


def strip_jsonb_gin(sql: str) -> str:
    sql = re.sub(
        r'CREATE INDEX "idx_sys_notice_target_account_ids_gin"[\s\S]*?;\s*',
        "",
        sql,
    )
    sql = re.sub(
        r'CREATE INDEX "idx_sys_notice_target_account_types_gin"[\s\S]*?;\s*',
        "",
        sql,
    )
    return sql


def write_postgresql(sql: str) -> None:
    header = (
        "/*\n"
        " HEI Boot PostgreSQL schema + seed\n"
        " Columns use json only (no jsonb type / jsonb GIN indexes).\n"
        "*/\n"
    )
    out = re.sub(r"/\*[\s\S]*?\*/", header, sql, count=1)
    leftover = [
        m.group(0)
        for m in re.finditer(r".{0,40}jsonb.{0,40}", out, flags=re.I)
        if "no jsonb" not in m.group(0).lower()
    ]
    if leftover:
        raise SystemExit("postgresql output still contains jsonb: " + repr(leftover[:5]))
    (ROOT / "scripts" / "db.postgresql.sql").write_text(out, encoding="utf-8")
    (ROOT / "scripts" / "db.sql").write_text(out, encoding="utf-8")
    print("wrote db.postgresql.sql / db.sql", len(out))


def convert_ident(name: str) -> str:
    return "`" + name.strip('"') + "`"


def convert_insert_values(line: str) -> str | None:
    # drop noisy / often-broken job logs for mysql seed stability
    if "sys_job_log" in line:
        return None
    # boolean literals as standalone tokens (repeat until stable for consecutive flags)
    prev = None
    while prev != line:
        prev = line
        line = re.sub(r", 't'([,)])", r", 1\1", line)
        line = re.sub(r", 'f'([,)])", r", 0\1", line)
        line = re.sub(r"\('t'([,)])", r"(1\1", line)
        line = re.sub(r"\('f'([,)])", r"(0\1", line)
    # timestamps with +00 / +08 etc
    line = re.sub(
        r"'(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}(?:\.\d+)?)[+-]\d{2}(?::?\d{2})?'",
        r"'\1'",
        line,
    )
    line = line.replace('"public".', "")
    line = re.sub(r'INSERT INTO "([^"]+)"', r"INSERT INTO `\1`", line)
    # mysql client treats \ as escape — double backslashes inside string literals
    out: list[str] = []
    in_str = False
    i = 0
    while i < len(line):
        c = line[i]
        if c == "'" and not in_str:
            in_str = True
            out.append(c)
            i += 1
            continue
        if c == "'" and in_str:
            if i + 1 < len(line) and line[i + 1] == "'":
                out.append("''")
                i += 2
                continue
            in_str = False
            out.append(c)
            i += 1
            continue
        if in_str and c == "\\":
            out.append("\\\\")
            i += 1
            continue
        out.append(c)
        i += 1
    return "".join(out)


def convert_create_table(block: str) -> str:
    block = re.sub(
        r'DROP TABLE IF EXISTS "public"\."([^"]+)";',
        r"DROP TABLE IF EXISTS `\1`;",
        block,
    )
    block = re.sub(
        r'CREATE TABLE "public"\."([^"]+)"\s*\(',
        r"CREATE TABLE `\1` (",
        block,
    )
    block = block.replace(' COLLATE "pg_catalog"."default"', "")
    block = re.sub(r'"([^"]+)"', lambda m: f"`{m.group(1)}`", block)
    block = block.replace("timestamptz(6)", "datetime(6)")
    block = block.replace("int4", "int")
    block = block.replace("int8", "bigint")
    block = block.replace("int2", "smallint")
    block = re.sub(r"\bbool\b", "tinyint(1)", block)
    block = re.sub(r"DEFAULT now\(\)", "DEFAULT CURRENT_TIMESTAMP(6)", block)
    block = block.replace("DEFAULT '{}'::json", "DEFAULT ('{}')")
    block = re.sub(r"DEFAULT '(\[\]|\{.*?\})'::json", r"DEFAULT ('\1')", block)
    # normalize ");" closing
    block = re.sub(r"\)\s*;", ");", block)
    return block


def convert_index_or_pk(line: str) -> str | None:
    if "jsonb" in line.lower() or " gin " in line.lower():
        return None
    # skip COMMENT ON
    if line.startswith("COMMENT ON"):
        return None
    line = line.replace('"public".', "")
    line = re.sub(r'"([^"]+)"', lambda m: f"`{m.group(1)}`", line)
    line = re.sub(r" USING btree ", " ", line)
    line = re.sub(r" USING gin ", " ", line)
    line = re.sub(r" COLLATE `pg_catalog`\.`default`", "", line)
    line = re.sub(r" `pg_catalog`\.`[^`]+`", "", line)
    line = re.sub(r" ASC NULLS (LAST|FIRST)", "", line)
    line = re.sub(r" DESC NULLS (LAST|FIRST)", " DESC", line)
    # drop PostgreSQL partial-index WHERE (...::text ...)
    line = re.sub(r"\)\s*WHERE[\s\S]*?;", ");", line)
    line = re.sub(r"::\w+", "", line)
    return line


def to_mysql(pg_sql: str) -> str:
    lines = pg_sql.splitlines(keepends=True)
    out: list[str] = [
        "/*\n",
        " HEI Boot MySQL 8 schema + seed (converted from PostgreSQL dump).\n",
        "*/\n",
        "SET NAMES utf8mb4;\n",
        "SET FOREIGN_KEY_CHECKS = 0;\n",
        "\n",
    ]
    i = 0
    while i < len(lines):
        line = lines[i]
        stripped = line.strip()
        if stripped.startswith("/*") and i == 0:
            # skip old header — already wrote new one; consume until */
            while i < len(lines) and "*/" not in lines[i]:
                i += 1
            i += 1
            continue
        if stripped.startswith("COMMENT ON"):
            i += 1
            continue
        if stripped.startswith("DROP TABLE") or stripped.startswith("CREATE TABLE"):
            buf = [line]
            i += 1
            while i < len(lines):
                buf.append(lines[i])
                if lines[i].strip() == ");" or lines[i].strip() == ";":
                    # CREATE ends with ); on its own sometimes after )
                    if "CREATE TABLE" in "".join(buf) and lines[i].strip() in (");", ")"):
                        i += 1
                        if i < len(lines) and lines[i].strip() == ";":
                            buf.append(lines[i])
                            i += 1
                        break
                    if stripped.startswith("DROP") and lines[i].strip().endswith(";"):
                        i += 1
                        break
                    i += 1
                    break
                i += 1
            block = "".join(buf)
            # fix CREATE ending: Navicat uses )\n;
            block = convert_create_table(block)
            if not block.rstrip().endswith(";"):
                block = block.rstrip() + ";\n"
            out.append(block)
            if not block.endswith("\n"):
                out.append("\n")
            continue
        if stripped.startswith("INSERT INTO"):
            converted = convert_insert_values(line)
            if converted:
                out.append(converted)
            i += 1
            continue
        if stripped.startswith("CREATE INDEX") or stripped.startswith("CREATE UNIQUE INDEX"):
            # may be multi-line
            buf = [line]
            i += 1
            while i < len(lines) and ";" not in buf[-1]:
                buf.append(lines[i])
                i += 1
            if ";" not in buf[-1] and i < len(lines):
                buf.append(lines[i])
                i += 1
            block = "".join(buf)
            converted = convert_index_or_pk(block)
            if converted:
                if not converted.rstrip().endswith(";"):
                    converted = converted.rstrip() + ";\n"
                out.append(converted if converted.endswith("\n") else converted + "\n")
            continue
        if stripped.startswith("ALTER TABLE"):
            buf = [line]
            i += 1
            while i < len(lines) and ";" not in "".join(buf):
                buf.append(lines[i])
                i += 1
            block = "".join(buf)
            converted = convert_index_or_pk(block)
            if converted:
                out.append(converted if converted.endswith("\n") else converted + "\n")
            continue
        # section comments and blanks
        if stripped.startswith("--") or stripped == "":
            out.append(line)
            i += 1
            continue
        # ignore leftover pg-only noise
        i += 1
    out.append("\nSET FOREIGN_KEY_CHECKS = 1;\n")
    return "".join(out)


def main() -> None:
    raw = SRC.read_text(encoding="utf-8")
    pg = strip_jsonb_gin(raw)
    write_postgresql(pg)
    mysql = to_mysql(pg)
    (ROOT / "scripts" / "db.mysql.sql").write_text(mysql, encoding="utf-8")
    print("wrote db.mysql.sql", len(mysql))
    if "jsonb" in mysql.lower():
        raise SystemExit("mysql output still contains jsonb")


if __name__ == "__main__":
    main()
