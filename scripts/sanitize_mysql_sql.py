#!/usr/bin/env python3
"""Sanitize scripts/db.mysql.sql for mysql client (escape \\ and drop broken job logs)."""

from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / "scripts" / "db.mysql.sql"


def escape_insert_backslashes(line: str) -> str:
    if not line.startswith("INSERT INTO"):
        return line
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


def main() -> None:
    text = SRC.read_text(encoding="utf-8")
    lines_out: list[str] = []
    for line in text.splitlines(True):
        if line.startswith("INSERT INTO") and "sys_job_log" in line:
            continue
        if line.startswith("INSERT INTO") and not (
            line.rstrip().endswith(");") or line.rstrip().endswith(";")
        ):
            continue
        lines_out.append(escape_insert_backslashes(line) if line.startswith("INSERT INTO") else line)
    SRC.write_text("".join(lines_out), encoding="utf-8")
    print("sanitized", SRC)


if __name__ == "__main__":
    main()
