#!/usr/bin/env python3
"""为 hei-boot 批量补齐 OpenAPI 文档注解（模型 @Schema、Controller @Tag/@Operation，供 Knife4j）。"""
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT))

from scripts.generate_column_labels import TABLE_LABELS
from scripts.db_column_labels import COMMON_COLUMN_LABELS, resolve_column_label

SCHEMA_IMPORT = "import io.swagger.v3.oas.annotations.media.Schema;"

EXTRA_DOMAIN = [
    ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/domain/ApiResponse.java",
    ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/domain/PageQuery.java",
    ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/domain/BaseEntity.java",
    ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/param/IdParam.java",
    ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/param/IdsParam.java",
]

ENUM_FILES = list((ROOT / "common/common-core/src/main/java/github/jiangbyte/io/common/core/enums").glob("*.java"))

FIELD_LABEL_OVERRIDES: dict[str, str] = {
    "account": "登录账号/用户名",
    "emailLoginEnabled": "是否启用邮箱登录",
    "phoneLoginEnabled": "是否启用手机号登录",
    "emailIdentity": "邮箱身份标识",
    "phoneIdentity": "手机号身份标识",
    "emailIdentityVerified": "邮箱身份是否已验证",
    "phoneIdentityVerified": "手机号身份是否已验证",
    "emailIdentityBindStatus": "邮箱身份绑定状态",
    "phoneIdentityBindStatus": "手机号身份绑定状态",
    "identities": "账号身份标识列表",
    "oauthBindings": "三方登录绑定列表",
    "children": "子节点列表",
    "isRead": "当前用户是否已读",
    "parentIdName": "父级名称（展示）",
    "moduleIdName": "模块名称（展示）",
    "moduleClient": "模块所属客户端（展示）",
    "permissionKey": "权限键",
    "moduleCode": "模块编码",
    "resourceCode": "资源编码",
    "action": "动作",
    "message": "提示信息",
    "data": "响应数据",
    "current": "当前页码（从 1 开始）",
    "size": "每页条数",
    "ids": "主键 ID 列表",
}


def camel_to_snake(name: str) -> str:
    s1 = re.sub(r"(.)([A-Z][a-z]+)", r"\1_\2", name)
    return re.sub(r"([a-z0-9])([A-Z])", r"\1_\2", s1).lower()


def clean_javadoc(text: str) -> str:
    if not text:
        return ""
    text = text.strip()
    if text.startswith("/**"):
        text = text[3:]
    if text.endswith("*/"):
        text = text[:-2]
    text = re.sub(r"^\s*\*\s?", "", text, flags=re.M)
    text = re.sub(r"\{@code\s+([^}]+)\}", r"\1", text)
    text = re.sub(r"\{@link\s+[^}]+\}", "", text)
    lines = [ln.strip() for ln in text.splitlines() if ln.strip()]
    lines = [ln for ln in lines if not ln.startswith("Author:")]
    if not lines:
        return ""
    first = lines[0]
    for sep in ("。", "；", ";"):
        if sep in first:
            first = first.split(sep, 1)[0] + "。"
            break
    return first.strip()


def escape_java_string(value: str) -> str:
    return value.replace("\\", "\\\\").replace('"', '\\"').replace("\n", " ").strip()


def schema_line(description: str, indent: str) -> str:
    return f'{indent}@Schema(description = "{escape_java_string(description)}")'


def infer_class_description(class_name: str, javadoc: str, table: str | None, kind: str) -> str:
    if javadoc:
        return clean_javadoc(javadoc)
    if kind == "entity" and table and table in TABLE_LABELS:
        return TABLE_LABELS[table]
    for suffix, label in (
        ("AddParam", "创建入参"),
        ("EditParam", "编辑入参"),
        ("PageParam", "分页查询入参"),
        ("SaveParam", "保存入参"),
        ("BatchSaveParam", "批量保存入参"),
        ("BatchItemParam", "批量项入参"),
        ("UpdateParam", "更新入参"),
        ("Result", "响应结果"),
    ):
        if class_name.endswith(suffix):
            return f"{class_name[: -len(suffix)]}{label}"
    return class_name


def infer_field_description(field_name: str, field_doc: str, table: str | None, class_name: str) -> str:
    if field_doc:
        return clean_javadoc(field_doc)
    if field_name in FIELD_LABEL_OVERRIDES:
        return FIELD_LABEL_OVERRIDES[field_name]
    snake = camel_to_snake(field_name)
    if table:
        label = resolve_column_label(table, snake)
        if label and label != snake:
            return label
    if snake in COMMON_COLUMN_LABELS:
        return COMMON_COLUMN_LABELS[snake]
    for stem in (
        class_name.removesuffix("Result"),
        re.sub(r"(Add|Edit|Page|Save|Batch|Update|My|Review)?Param$", "", class_name),
    ):
        guess_table = camel_to_snake(stem)
        label = resolve_column_label(guess_table, snake, "")
        if label and label != snake:
            return label
    return field_name


def extract_table_name(content: str, class_name: str) -> str | None:
    m = re.search(r'@TableName\(\s*(?:value\s*=\s*)?"([^"]+)"', content)
    if m:
        return m.group(1)
    snake = camel_to_snake(class_name)
    return snake if snake in TABLE_LABELS else None


def parse_class_javadoc(lines: list[str], class_idx: int) -> str:
    i = class_idx - 1
    while i >= 0:
        stripped = lines[i].strip()
        if stripped == "" or stripped.startswith("@"):
            i -= 1
            continue
        if stripped.startswith("/**") and stripped.endswith("*/"):
            return clean_javadoc(lines[i])
        if stripped == "*/":
            end = i
            while i >= 0:
                if lines[i].strip().startswith("/**"):
                    return clean_javadoc("\n".join(lines[i : end + 1]))
                i -= 1
            return ""
        break
    return ""


def ensure_import(lines: list[str]) -> list[str]:
    if any(SCHEMA_IMPORT in ln for ln in lines):
        return lines
    out: list[str] = []
    inserted = False
    for i, line in enumerate(lines):
        out.append(line)
        if not inserted and line.startswith("package "):
            # after package blank line or before first import
            continue
        if not inserted and line.startswith("import "):
            out.insert(len(out) - 1, SCHEMA_IMPORT)
            inserted = True
    if not inserted:
        # no imports: after package
        for i, line in enumerate(out):
            if line.startswith("package "):
                out.insert(i + 1, "")
                out.insert(i + 2, SCHEMA_IMPORT)
                break
    return out


def is_field_line(line: str) -> bool:
    s = line.strip()
    return (
        s.startswith("private ")
        and " static " not in f" {s} "
        and not s.startswith("private static")
        and ";" in s
    )


def field_name_from_line(line: str) -> str | None:
    m = re.search(r"private\s+[\w.<>,\s?]+\s+(\w+)\s*(?:=|;)", line.strip())
    return m.group(1) if m else None


def collect_block_doc(lines: list[str], start: int) -> tuple[str, int]:
    i = start - 1
    while i >= 0 and lines[i].strip() == "":
        i -= 1
    block_end = i
    while i >= 0 and lines[i].strip().startswith("@"):
        i -= 1
        while i >= 0 and lines[i].strip() == "":
            i -= 1
    if i >= 0:
        stripped = lines[i].strip()
        if stripped.startswith("/**") and stripped.endswith("*/"):
            return stripped, i
        if stripped == "*/":
            end = i
            while i >= 0:
                if lines[i].strip().startswith("/**"):
                    return "\n".join(lines[i : end + 1]), i
                i -= 1
    return "", block_end + 1


def record_component_name(line: str) -> str | None:
    stripped = line.strip().rstrip(",")
    if not stripped or stripped.startswith("@") or stripped in {"(", ")"}:
        return None
    m = re.search(r"(?:@\w+(?:\([^)]*\))?\s+)*([\w.<>,\s?]+)\s+(\w+)\s*$", stripped)
    return m.group(2) if m else None


def process_record(path: Path) -> bool:
    original = path.read_text(encoding="utf-8")
    if "@Schema(" in original:
        return False
    lines = original.splitlines()
    record_idx = next(
        (i for i, ln in enumerate(lines) if re.search(r"public\s+record\s+(\w+)\s*\(", ln)),
        None,
    )
    if record_idx is None:
        return False

    class_m = re.search(r"public\s+record\s+(\w+)", lines[record_idx])
    class_name = class_m.group(1)
    posix = path.as_posix()
    kind = "result" if "/result/" in posix else "model"
    table = None
    if kind == "result":
        stem = re.sub(r"Result$", "", class_name)
        guess = camel_to_snake(stem)
        table = guess if guess in TABLE_LABELS else table

    class_desc = infer_class_description(class_name, parse_class_javadoc(lines, record_idx), table, kind)
    indent_class = re.match(r"(\s*)", lines[record_idx]).group(1)
    insert_pos = record_idx
    while insert_pos > 0 and lines[insert_pos - 1].strip().startswith("@"):
        insert_pos -= 1
    lines.insert(insert_pos, schema_line(class_desc, indent_class))

    record_idx = next(i for i, ln in enumerate(lines) if re.search(r"public\s+record\s+\w+\s*\(", ln))
    close_idx = record_idx
    while close_idx < len(lines) and ")" not in lines[close_idx]:
        close_idx += 1
    component_indices = [
        i
        for i in range(record_idx + 1, close_idx + 1)
        if record_component_name(lines[i]) is not None
    ]
    for idx in reversed(component_indices):
        line = lines[idx]
        fname = record_component_name(line)
        if not fname:
            continue
        field_indent = re.match(r"(\s*)", line).group(1)
        desc = infer_field_description(fname, "", table, class_name)
        lines.insert(idx, schema_line(desc, field_indent))

    lines = ensure_import(lines)
    new_content = "\n".join(lines) + ("\n" if original.endswith("\n") else "")
    if new_content != original:
        path.write_text(new_content, encoding="utf-8")
        return True
    return False


def process_file(path: Path) -> bool:
    original = path.read_text(encoding="utf-8")
    if re.search(r"public\s+record\s+\w+", original):
        return process_record(path)
    lines = [ln for ln in original.splitlines() if "@Schema(" not in ln]

    class_idx = next(
        (i for i, ln in enumerate(lines) if re.search(r"public\s+(?:abstract\s+)?class\s+(\w+)", ln)),
        None,
    )
    if class_idx is None:
        return False

    class_m = re.search(r"public\s+(?:abstract\s+)?class\s+(\w+)", lines[class_idx])
    class_name = class_m.group(1)

    posix = path.as_posix()
    kind = "model"
    if "/entity/" in posix:
        kind = "entity"
    elif "/param/" in posix:
        kind = "param"
    elif "/result/" in posix:
        kind = "result"

    table = extract_table_name(original, class_name) if kind == "entity" else None
    if kind == "param":
        stem = re.sub(r"(Add|Edit|Page|Save|Batch|Update|My|Review)?Param$", "", class_name)
        guess = camel_to_snake(stem)
        table = guess if guess in TABLE_LABELS else table
    elif kind == "result":
        stem = re.sub(r"Result$", "", class_name)
        guess = camel_to_snake(stem)
        table = guess if guess in TABLE_LABELS else table

    class_desc = infer_class_description(class_name, parse_class_javadoc(lines, class_idx), table, kind)
    indent_class = re.match(r"(\s*)", lines[class_idx]).group(1)
    # 插在 public class 前（保留已有类注解如 @Data 在其上方）
    insert_pos = class_idx
    while insert_pos > 0 and lines[insert_pos - 1].strip().startswith("@"):
        insert_pos -= 1
    lines.insert(insert_pos, schema_line(class_desc, indent_class))

    # 字段：从后往前插入 @Schema，避免下标错位
    class_idx = next(i for i, ln in enumerate(lines) if re.search(r"public\s+(?:abstract\s+)?class\s+", ln))
    field_indices = [i for i in range(class_idx + 1, len(lines)) if is_field_line(lines[i])]
    for idx in reversed(field_indices):
        line = lines[idx]
        fname = field_name_from_line(line)
        if not fname or fname == "serialVersionUID":
            continue
        field_doc, block_start = collect_block_doc(lines, idx)
        field_indent = re.match(r"(\s*)", line).group(1)
        desc = infer_field_description(fname, field_doc, table, class_name)
        # 插在字段块最前（javadoc/注解之前）
        lines.insert(block_start, schema_line(desc, field_indent))

    lines = ensure_import(lines)
    new_content = "\n".join(lines) + ("\n" if original.endswith("\n") else "")
    if new_content != original:
        path.write_text(new_content, encoding="utf-8")
        return True
    return False


def process_enum(path: Path) -> bool:
    original = path.read_text(encoding="utf-8")
    lines = [ln for ln in original.splitlines() if "@Schema(" not in ln]
    enum_idx = next((i for i, ln in enumerate(lines) if re.search(r"public\s+enum\s+(\w+)", ln)), None)
    if enum_idx is None:
        return False
    name = re.search(r"public\s+enum\s+(\w+)", lines[enum_idx]).group(1)
    desc = infer_class_description(name, parse_class_javadoc(lines, enum_idx), None, "enum")
    indent = re.match(r"(\s*)", lines[enum_idx]).group(1)
    insert_pos = enum_idx
    while insert_pos > 0 and lines[insert_pos - 1].strip().startswith("@"):
        insert_pos -= 1
    lines.insert(insert_pos, schema_line(desc, indent))
    lines = ensure_import(lines)
    new_content = "\n".join(lines) + ("\n" if original.endswith("\n") else "")
    if new_content != original:
        path.write_text(new_content, encoding="utf-8")
        return True
    return False


def collect_model_files() -> list[Path]:
    files: list[Path] = []
    for base in (ROOT / "module", ROOT / "module-api"):
        if not base.exists():
            continue
        for sub in ("entity", "param", "result"):
            files.extend(base.rglob(f"{sub}/*.java"))
    files.extend(p for p in EXTRA_DOMAIN if p.exists())
    return sorted(set(files))


TAG_IMPORT = "import io.swagger.v3.oas.annotations.tags.Tag;"
OPERATION_IMPORT = "import io.swagger.v3.oas.annotations.Operation;"
HIDDEN_IMPORT = "import io.swagger.v3.oas.annotations.Hidden;"

HIDDEN_CONTROLLERS = {"InternalHealthController", "RootController"}

MAPPING_PREFIXES = (
    "@GetMapping",
    "@PostMapping",
    "@PutMapping",
    "@DeleteMapping",
    "@PatchMapping",
)

ACTION_LABELS: dict[str, str] = {
    "create": "创建",
    "update": "更新",
    "delete": "删除",
    "detail": "查询详情",
    "page": "分页查询",
    "tree": "树形查询",
    "list": "列表查询",
    "login": "登录",
    "logout": "登出",
    "refresh": "刷新会话",
    "register": "注册",
    "publish": "发布",
    "revoke": "撤回",
    "pin": "置顶",
    "approve": "审核通过",
    "reject": "审核驳回",
    "upload": "上传",
    "download": "下载",
    "export": "导出",
    "import": "导入",
    "preview": "预览",
    "generate": "生成代码",
    "sync": "同步",
    "bind": "绑定",
    "unbind": "解绑",
    "enable": "启用",
    "disable": "停用",
    "run": "执行",
    "stop": "停止",
    "reply": "回复",
    "read": "标记已读",
    "captcha": "获取图形验证码",
    "auth-options": "获取登录页配置",
    "password-key": "获取密码传输公钥",
    "send-login-code": "发送登录验证码",
    "forgot-password": "忘记密码",
    "forgot-password-phone": "手机号忘记密码",
    "reset-password": "重置密码",
    "reset-password-phone": "手机号重置密码",
    "cancel": "注销账号",
    "my-page": "查询本人记录分页",
    "my-detail": "查询本人记录详情",
    "children": "子资源",
    "current": "查询当前用户菜单",
    "options": "查询选项",
    "footer": "查询页脚配置",
    "authorize": "OAuth 授权",
    "exchange": "OAuth 换票",
    "exit": "退出会话",
    "kick": "踢出会话",
    "analysis": "会话分析",
}


def tag_line(name: str, indent: str) -> str:
    return f'{indent}@Tag(name = "{escape_java_string(name)}")'


def operation_line(summary: str, indent: str) -> str:
    return f'{indent}@Operation(summary = "{escape_java_string(summary)}")'


def hidden_line(indent: str) -> str:
    return f"{indent}@Hidden"


def infer_tag_name(javadoc: str, class_name: str) -> str:
    if javadoc:
        text = clean_javadoc(javadoc)
        if text.endswith("。"):
            text = text[:-1]
        for sep in ("：", ":"):
            if sep in text:
                return text.split(sep, 1)[0].strip()
        return text
    return re.sub(r"Controller$", "", class_name)


def infer_operation_summary(javadoc: str, mapping_path: str, method_name: str) -> str:
    if javadoc:
        text = clean_javadoc(javadoc)
        return text if text.endswith("。") else text + "。"
    segment = mapping_path.rstrip("/").split("/")[-1] if mapping_path else method_name
    if segment in ACTION_LABELS:
        return ACTION_LABELS[segment] + "。"
    return segment.replace("-", " ") + "。"


def extract_mapping_path(line: str) -> str:
    m = re.search(r'@\w+Mapping\([^)]*["\']([^"\']+)["\']', line)
    return m.group(1) if m else ""


def is_mapping_line(line: str) -> bool:
    stripped = line.strip()
    return any(stripped.startswith(prefix) for prefix in MAPPING_PREFIXES)


def find_public_method_line(lines: list[str], mapping_idx: int) -> int | None:
    for i in range(mapping_idx, min(mapping_idx + 8, len(lines))):
        stripped = lines[i].strip()
        if stripped.startswith("public ") and not stripped.startswith("public static "):
            return i
    return None


def method_name_from_public_line(line: str) -> str:
    m = re.search(r"public\s+[\w.<>,\s?]+\s+(\w+)\s*\(", line.strip())
    return m.group(1) if m else ""


def strip_openapi_controller_annotations(lines: list[str]) -> list[str]:
    return [
        ln
        for ln in lines
        if not ln.strip().startswith(("@Tag(", "@Operation(", "@Hidden"))
    ]


def operation_insert_pos(lines: list[str], mapping_idx: int) -> int:
    method_doc, doc_start = collect_block_doc(lines, mapping_idx)
    if not method_doc:
        return mapping_idx
    end = doc_start
    while end < mapping_idx and "*/" not in lines[end]:
        end += 1
    return end + 1 if end < mapping_idx else doc_start


def ensure_imports(lines: list[str], imports: list[str]) -> list[str]:
    for imp in imports:
        if any(imp in ln for ln in lines):
            continue
        inserted = False
        for i, line in enumerate(lines):
            if line.startswith("import "):
                lines.insert(i, imp)
                inserted = True
                break
        if not inserted:
            for i, line in enumerate(lines):
                if line.startswith("package "):
                    lines.insert(i + 1, "")
                    lines.insert(i + 2, imp)
                    break
    return lines


def collect_controller_files() -> list[Path]:
    files: list[Path] = []
    for base in (ROOT / "module", ROOT / "app"):
        if base.exists():
            files.extend(base.rglob("*Controller.java"))
    return sorted(set(files))


def process_controller(path: Path) -> bool:
    original = path.read_text(encoding="utf-8")
    if "@RestController" not in original:
        return False
    lines = strip_openapi_controller_annotations(original.splitlines())
    class_idx = next(
        (i for i, ln in enumerate(lines) if re.search(r"public\s+class\s+(\w+)", ln)),
        None,
    )
    if class_idx is None:
        return False
    class_name = re.search(r"public\s+class\s+(\w+)", lines[class_idx]).group(1)
    indent_class = re.match(r"(\s*)", lines[class_idx]).group(1)
    class_doc = parse_class_javadoc(lines, class_idx)
    hide = class_name in HIDDEN_CONTROLLERS

    insert_pos = class_idx
    while insert_pos > 0 and lines[insert_pos - 1].strip().startswith("@"):
        insert_pos -= 1
    if hide:
        lines.insert(insert_pos, hidden_line(indent_class))
    else:
        lines.insert(insert_pos, tag_line(infer_tag_name(class_doc, class_name), indent_class))

    mapping_indices = [i for i, ln in enumerate(lines) if is_mapping_line(ln)]
    for idx in reversed(mapping_indices):
        if find_public_method_line(lines, idx) is None:
            continue
        if hide:
            continue
        method_doc, _ = collect_block_doc(lines, idx)
        mapping_path = extract_mapping_path(lines[idx])
        method_name = method_name_from_public_line(lines[find_public_method_line(lines, idx) or idx])
        summary = infer_operation_summary(method_doc, mapping_path, method_name)
        indent = re.match(r"(\s*)", lines[idx]).group(1)
        lines.insert(operation_insert_pos(lines, idx), operation_line(summary, indent))

    imports = [OPERATION_IMPORT]
    if hide:
        imports.append(HIDDEN_IMPORT)
    else:
        imports.append(TAG_IMPORT)
    lines = ensure_imports(lines, imports)
    new_content = "\n".join(lines) + ("\n" if original.endswith("\n") else "")
    if new_content != original:
        path.write_text(new_content, encoding="utf-8")
        return True
    return False


def process_models() -> int:
    changed = 0
    for path in collect_model_files():
        if process_file(path):
            changed += 1
    for path in ENUM_FILES:
        if process_enum(path):
            changed += 1
    return changed


def process_controllers() -> int:
    changed = 0
    for path in collect_controller_files():
        if process_controller(path):
            changed += 1
    return changed


def main() -> None:
    model_changed = process_models()
    controller_changed = process_controllers()
    print(f"updated {model_changed} model/enum files, {controller_changed} controller files")


if __name__ == "__main__":
    main()
