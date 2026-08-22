"""Update sys_job / sys_job_log column names in SQL dump files."""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
FILES = [
    ROOT / "scripts" / "db.postgresql.sql",
    ROOT / "scripts" / "db.mysql.sql",
    ROOT / "scripts" / "db.sql",
]

REPLACEMENTS = [
    ("job_name", "name"),
    ("execute_class", "handler"),
    ("execute_type", "trigger_type"),
    ("execute_param", "params"),
    ("last_execute_result", "last_result"),
    ("execute_time", "started_at"),
    ("execute_duration_ms", "duration_ms"),
    ("execute_result", "result"),
    ("idx_sys_job_log_execute_time", "idx_sys_job_log_started_at"),
    ("执行类（JobHandler 全限定类名）", "处理器标识（Boot 为 JobHandler 全限定类名，其他栈为注册 key）"),
]


def strip_log_name_column(text: str) -> str:
    text = re.sub(
        r'(\s+"job_id" varchar\(64\)[^\n]+\n)\s+"name" varchar\(128\)[^\n]+\n',
        r"\1",
        text,
        count=1,
    )
    text = re.sub(
        r"(\s+`job_id` varchar\(64\)[^\n]+\n)\s+`name` varchar\(128\)[^\n]+\n",
        r"\1",
        text,
        count=1,
    )
    return text


def strip_log_insert_name(text: str) -> str:
    pg_pattern = re.compile(
        r"(INSERT INTO \"public\"\.\"sys_job_log\" VALUES \()"
        r"('(?:[^']|'')*',\s*'(?:[^']|'')*',\s*)"
        r"'(?:[^']|'')*',\s*"
    )
    mysql_pattern = re.compile(
        r"(INSERT INTO `sys_job_log` VALUES \()"
        r"('(?:[^']|'')*',\s*'(?:[^']|'')*',\s*)"
        r"'(?:[^']|'')*',\s*"
    )
    text = pg_pattern.sub(r"\1\2", text)
    text = mysql_pattern.sub(r"\1\2", text)
    return text


def main() -> None:
    for path in FILES:
        text = path.read_text(encoding="utf-8")
        for old, new in REPLACEMENTS:
            text = text.replace(old, new)
        text = strip_log_name_column(text)
        text = strip_log_insert_name(text)
        text = text.replace(
            'COMMENT ON COLUMN "public"."sys_job_log"."name" IS \'任务名称\';\n',
            "",
        )
        path.write_text(text, encoding="utf-8")
        print(f"updated {path.name}")


if __name__ == "__main__":
    main()
