#!/usr/bin/env python3
"""Run identity migration against PostgreSQL or MySQL."""

from __future__ import annotations

import argparse
import os
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent


def split_sql(content: str) -> list[str]:
    statements: list[str] = []
    current: list[str] = []
    for line in content.splitlines():
        stripped = line.strip()
        if stripped.startswith("--"):
            continue
        current.append(line)
        if stripped.endswith(";"):
            statement = "\n".join(current).strip()
            if statement:
                statements.append(statement)
            current = []
    tail = "\n".join(current).strip()
    if tail:
        statements.append(tail)
    return statements


def run_postgresql(sql_path: Path, host: str, port: int, user: str, password: str, database: str) -> None:
    try:
        import psycopg2
    except ImportError as exc:
        raise SystemExit("psycopg2 is required for PostgreSQL. Install with: pip install psycopg2-binary") from exc

    content = sql_path.read_text(encoding="utf-8")
    conn = psycopg2.connect(host=host, port=port, user=user, password=password, dbname=database)
    conn.autocommit = True
    try:
        with conn.cursor() as cur:
            for statement in split_sql(content):
                cur.execute(statement)
    finally:
        conn.close()


def run_mysql(sql_path: Path, host: str, port: int, user: str, password: str, database: str) -> None:
    import pymysql

    content = sql_path.read_text(encoding="utf-8")
    conn = pymysql.connect(host=host, port=port, user=user, password=password, database=database, autocommit=True)
    try:
        with conn.cursor() as cur:
            for statement in split_sql(content):
                cur.execute(statement)
    finally:
        conn.close()


def detect_engine(host: str, port: int, user: str, password: str, database: str) -> str | None:
    try:
        import pymysql

        conn = pymysql.connect(host=host, port=port, user=user, password=password, database=database, connect_timeout=3)
        conn.close()
        return "mysql"
    except Exception:
        pass

    try:
        import psycopg2

        conn = psycopg2.connect(host=host, port=port, user=user, password=password, dbname=database, connect_timeout=3)
        conn.close()
        return "postgresql"
    except Exception:
        return None


def main() -> int:
    parser = argparse.ArgumentParser(description="Apply real-name identity migration")
    parser.add_argument("--engine", choices=("postgresql", "mysql", "auto"), default="auto")
    parser.add_argument("--host", default=os.getenv("DB_HOST", "127.0.0.1"))
    parser.add_argument("--port", type=int, default=None)
    parser.add_argument("--user", default=os.getenv("DB_WRITE_USERNAME"))
    parser.add_argument("--password", default=os.getenv("DB_WRITE_PASSWORD", "123456"))
    parser.add_argument("--database", default=os.getenv("DB_NAME", "hei_boot"))
    args = parser.parse_args()

    engine = args.engine
    if engine == "auto":
        engine = detect_engine(args.host, args.port or 5432, args.user or "postgres", args.password, args.database)
        if engine is None:
            engine = detect_engine(args.host, args.port or 3306, args.user or "root", args.password, args.database)
        if engine is None:
            print("Could not connect to PostgreSQL or MySQL with provided credentials.", file=sys.stderr)
            return 1

    if engine == "postgresql":
        user = args.user or "postgres"
        port = args.port or 5432
        sql_path = ROOT / "migration" / "20260822_real_name_identity.postgresql.sql"
        run_postgresql(sql_path, args.host, port, user, args.password, args.database)
    else:
        user = args.user or "root"
        port = args.port or 3306
        sql_path = ROOT / "migration" / "20260822_real_name_identity.mysql.sql"
        run_mysql(sql_path, args.host, port, user, args.password, args.database)

    print(f"Migration applied successfully using {engine} ({args.host}:{port}/{args.database}).")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
