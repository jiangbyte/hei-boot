""" Author: Charlie

OpenAPI 契约：解析 schema、生成最小入参、校验出参 JSON。
适配 Springdoc `/v3/api-docs` 与 Stringly 序列化（数字/布尔常为字符串）。
"""

from __future__ import annotations

import copy
import json
from typing import Any
from urllib.request import Request, urlopen

from jsonschema import Draft202012Validator
from referencing import Registry, Resource
from referencing.jsonschema import DRAFT202012


def fetch_openapi(base: str) -> dict[str, Any]:
    root = base.rstrip("/")
    for path in ("/v3/api-docs", "/v3/api-docs/default"):
        req = Request(root + path)
        with urlopen(req, timeout=60) as resp:
            doc = json.loads(resp.read().decode("utf-8"))
        if isinstance(doc, dict) and doc.get("paths"):
            return doc
        urls = doc.get("urls") if isinstance(doc, dict) else None
        if isinstance(urls, list) and urls:
            merged: dict[str, Any] = {
                "openapi": doc.get("openapi") or "3.0.1",
                "paths": {},
                "components": {"schemas": {}},
            }
            for item in urls:
                href = str((item or {}).get("url") or "")
                if not href:
                    continue
                if href.startswith("/"):
                    href = root + href
                with urlopen(Request(href), timeout=60) as resp:
                    part = json.loads(resp.read().decode("utf-8"))
                if not isinstance(part, dict):
                    continue
                merged["paths"].update(part.get("paths") or {})
                schemas = ((part.get("components") or {}).get("schemas")) or {}
                merged["components"]["schemas"].update(schemas)
            if merged["paths"]:
                return merged
    raise RuntimeError("openapi not found at /v3/api-docs")


def build_registry(openapi: dict[str, Any]) -> Registry:
    schemas = (openapi.get("components") or {}).get("schemas") or {}
    resources: dict[str, Resource] = {}
    for name, schema in schemas.items():
        if isinstance(schema, dict):
            resources[f"#/components/schemas/{name}"] = Resource.from_contents(
                schema, default_specification=DRAFT202012
            )
    resources[""] = Resource.from_contents(openapi, default_specification=DRAFT202012)
    registry: Registry = Registry()
    for uri, resource in resources.items():
        registry = registry.with_resource(uri, resource)
    return registry


def resolve_ref(openapi: dict[str, Any], node: Any, *, _seen: set[str] | None = None) -> Any:
    if not isinstance(node, dict):
        if isinstance(node, list):
            return [resolve_ref(openapi, i, _seen=_seen) for i in node]
        return node
    seen = set() if _seen is None else _seen
    ref = node.get("$ref")
    if isinstance(ref, str) and ref.startswith("#/"):
        if ref in seen:
            return {"type": "object"}
        seen = set(seen)
        seen.add(ref)
        cur: Any = openapi
        for part in ref[2:].split("/"):
            part = part.replace("~1", "/").replace("~0", "~")
            if not isinstance(cur, dict) or part not in cur:
                return {"type": "object"}
            cur = cur[part]
        merged = resolve_ref(openapi, copy.deepcopy(cur), _seen=seen)
        extras = {k: v for k, v in node.items() if k != "$ref"}
        if extras and isinstance(merged, dict):
            out = copy.deepcopy(merged)
            for ek, ev in extras.items():
                out[ek] = resolve_ref(openapi, ev, _seen=seen)
            return out
        return merged

    out: dict[str, Any] = {}
    for k, v in node.items():
        if k in {"allOf", "oneOf", "anyOf"} and isinstance(v, list):
            out[k] = [resolve_ref(openapi, i, _seen=seen) for i in v]
        elif k == "properties" and isinstance(v, dict):
            out[k] = {pk: resolve_ref(openapi, pv, _seen=_seen) for pk, pv in v.items()}
        elif k == "items":
            out[k] = resolve_ref(openapi, v, _seen=seen)
        elif k == "additionalProperties" and isinstance(v, dict):
            out[k] = resolve_ref(openapi, v, _seen=seen)
        else:
            out[k] = resolve_ref(openapi, v, _seen=seen) if isinstance(v, (dict, list)) else v
    return out


def _string_for_schema(schema: dict[str, Any]) -> str:
    if schema.get("format") == "date-time":
        return "2026-01-01T00:00:00Z"
    if schema.get("format") == "email":
        return "e2e@example.com"
    if schema.get("format") == "uri":
        return "https://example.com"
    pattern = str(schema.get("pattern") or "")
    min_len = int(schema.get("minLength") or 1)
    max_len = schema.get("maxLength")
    if pattern in {r"^[A-Z0-9_]+$", "^[A-Z0-9_]+$"}:
        base = "E2ECODE1"
    elif "A-Z" in pattern and "0-9" in pattern:
        base = "E2ECODE1"
    elif pattern.startswith("^") and pattern.endswith("$") and "[" not in pattern:
        base = pattern[1:-1] or "x"
    else:
        base = "x" * max(min_len, 1)
    if max_len is not None:
        base = base[: int(max_len)]
    if len(base) < min_len:
        base = (base + ("1" * min_len))[:min_len]
    return base


def _first_type(schema: dict[str, Any]) -> str | None:
    t = schema.get("type")
    if isinstance(t, list):
        for item in t:
            if item != "null":
                return str(item)
        return str(t[0]) if t else None
    if isinstance(t, str):
        return t
    if "properties" in schema or schema.get("additionalProperties") is not None:
        return "object"
    if "items" in schema:
        return "array"
    if "enum" in schema and schema["enum"]:
        sample = schema["enum"][0]
        if isinstance(sample, bool):
            return "boolean"
        if isinstance(sample, int) and not isinstance(sample, bool):
            return "integer"
        if isinstance(sample, float):
            return "number"
        if isinstance(sample, str):
            return "string"
    return None


def generate_example(openapi: dict[str, Any], schema: dict[str, Any] | None, *, depth: int = 0) -> Any:
    if schema is None or depth > 8:
        return None
    schema = resolve_ref(openapi, schema)
    if not isinstance(schema, dict):
        return None

    if "example" in schema:
        return schema["example"]
    if "default" in schema:
        return schema["default"]
    if "const" in schema:
        return schema["const"]
    if "enum" in schema and schema["enum"]:
        return schema["enum"][0]

    for key in ("anyOf", "oneOf"):
        opts = schema.get(key)
        if isinstance(opts, list) and opts:
            non_null = [
                o for o in opts if not (isinstance(o, dict) and o.get("type") == "null")
            ]
            return generate_example(openapi, non_null[0] if non_null else opts[0], depth=depth + 1)

    all_of = schema.get("allOf")
    if isinstance(all_of, list) and all_of:
        merged: dict[str, Any] = {"type": "object", "properties": {}, "required": []}
        for part in all_of:
            part_r = resolve_ref(openapi, part) if isinstance(part, dict) else {}
            if not isinstance(part_r, dict):
                continue
            props = part_r.get("properties") or {}
            if isinstance(props, dict):
                merged["properties"].update(props)
            req = part_r.get("required") or []
            if isinstance(req, list):
                merged["required"] = list(dict.fromkeys([*merged["required"], *req]))
            for k, v in part_r.items():
                if k not in {"properties", "required"}:
                    merged.setdefault(k, v)
        return generate_example(openapi, merged, depth=depth + 1)

    t = _first_type(schema)
    if t == "object" or (t is None and "properties" in schema):
        props = schema.get("properties") or {}
        required = list(schema.get("required") or [])
        obj: dict[str, Any] = {}
        if isinstance(props, dict):
            keys = required if required else list(props.keys())[:8]
            for name in keys:
                if name in props:
                    obj[name] = generate_example(openapi, props[name], depth=depth + 1)
        return obj
    if t == "array":
        items = schema.get("items")
        if isinstance(items, dict):
            return [generate_example(openapi, items, depth=depth + 1)]
        return []
    if t == "boolean":
        return True
    if t == "integer":
        return int(schema.get("minimum") or 1)
    if t == "number":
        return float(schema.get("minimum") or 1)
    if t == "string" or t is None:
        return _string_for_schema(schema)
    return None


def response_json_schema(openapi: dict[str, Any], op: dict[str, Any], status: str) -> dict[str, Any] | None:
    responses = op.get("responses") or {}
    resp = responses.get(status) or (responses.get(str(int(status))) if status.isdigit() else None)
    if not isinstance(resp, dict):
        # Springdoc 常用 default / 2XX
        resp = responses.get("default") or responses.get("2XX")
    if not isinstance(resp, dict):
        return None
    content = resp.get("content") or {}
    app_json = content.get("application/json") or content.get("*/*")
    if not isinstance(app_json, dict):
        return None
    schema = app_json.get("schema")
    return schema if isinstance(schema, dict) else None


def request_body_schema(openapi: dict[str, Any], op: dict[str, Any]) -> dict[str, Any] | None:
    body = op.get("requestBody")
    if not isinstance(body, dict):
        return None
    content = body.get("content") or {}
    for ctype in ("application/json", "multipart/form-data", "application/x-www-form-urlencoded"):
        block = content.get(ctype)
        if isinstance(block, dict) and isinstance(block.get("schema"), dict):
            return block["schema"]
    return None


def has_json_200(openapi: dict[str, Any], op: dict[str, Any]) -> bool:
    return response_json_schema(openapi, op, "200") is not None


def _types(schema: dict[str, Any]) -> list[str]:
    t = schema.get("type")
    if isinstance(t, list):
        return [str(x) for x in t]
    if isinstance(t, str):
        return [t]
    return []


def coerce_instance(schema: dict[str, Any], instance: Any) -> Any:
    """把 Stringly JSON（数字/布尔写成字符串）转成 schema 期望类型。"""
    if not isinstance(schema, dict) or instance is None:
        return instance
    types = _types(schema)
    if ("integer" in types or "number" in types) and isinstance(instance, str):
        try:
            if "integer" in types and "." not in instance:
                return int(instance)
            return float(instance)
        except ValueError:
            pass
    if "boolean" in types and isinstance(instance, str):
        low = instance.strip().lower()
        if low in {"true", "1"}:
            return True
        if low in {"false", "0"}:
            return False
    if "string" in types and isinstance(instance, (int, float, bool)):
        if isinstance(instance, bool):
            return "true" if instance else "false"
        return str(instance)
    if "object" in types or "properties" in schema:
        if isinstance(instance, dict):
            props = schema.get("properties") or {}
            out: dict[str, Any] = {}
            for k, v in instance.items():
                if isinstance(props, dict) and k in props and isinstance(props[k], dict):
                    out[k] = coerce_instance(props[k], v)
                else:
                    out[k] = v
            return out
    if "array" in types and isinstance(instance, list):
        items = schema.get("items")
        if isinstance(items, dict):
            return [coerce_instance(items, i) for i in instance]
    return instance


def _has_null_option(opts: list[Any]) -> bool:
    for o in opts:
        if not isinstance(o, dict):
            continue
        t = o.get("type")
        if t == "null" or (isinstance(t, list) and "null" in t):
            return True
    return False


def loosen_schema(schema: Any) -> Any:
    """允许额外字段、标量 null，去掉 OpenAPI 扩展。"""
    if isinstance(schema, list):
        return [loosen_schema(i) for i in schema]
    if not isinstance(schema, dict):
        return schema
    out: dict[str, Any] = {}
    for k, v in schema.items():
        if str(k).startswith("x-"):
            continue
        out[k] = loosen_schema(v)
    all_of = out.pop("allOf", None)
    if isinstance(all_of, list):
        props: dict[str, Any] = dict(out.get("properties") or {})
        required: list[Any] = list(out.get("required") or [])
        for part in all_of:
            if not isinstance(part, dict):
                continue
            pp = part.get("properties") or {}
            if isinstance(pp, dict):
                props.update(pp)
            rq = part.get("required") or []
            if isinstance(rq, list):
                required = list(dict.fromkeys([*required, *rq]))
            for pk, pv in part.items():
                if pk not in {"properties", "required", "type"}:
                    out.setdefault(pk, pv)
            pt = part.get("type")
            if pt and "type" not in out:
                out["type"] = pt
        if props:
            out["properties"] = props
        if required:
            out["required"] = required
    if out.get("type") == "object" or "properties" in out:
        out["additionalProperties"] = True
    t = out.get("type")
    if isinstance(t, str) and t in {"string", "integer", "number", "boolean", "object", "array"}:
        out["type"] = [t, "null"]
    elif isinstance(t, list) and "null" not in t:
        out["type"] = [*t, "null"]
    for key in ("anyOf", "oneOf"):
        opts = out.get(key)
        if isinstance(opts, list) and opts and not _has_null_option(opts):
            out[key] = [*opts, {"type": "null"}]
    if out.get("nullable") is True:
        out.pop("nullable", None)
    return out


def _types_of(schema: dict[str, Any]) -> list[str]:
    t = schema.get("type")
    if isinstance(t, list):
        return [str(x) for x in t]
    if isinstance(t, str):
        return [t]
    return []


def _widen_scalar(schema: dict[str, Any], instance: Any) -> dict[str, Any] | None:
    types = _types_of(schema)
    if isinstance(instance, str) and "string" not in types:
        return {"type": ["string", "null"]}
    if isinstance(instance, bool) and "boolean" not in types:
        return {"type": ["boolean", "null"]}
    if isinstance(instance, (int, float)) and not isinstance(instance, bool):
        if "integer" not in types and "number" not in types:
            return {"type": ["number", "integer", "string", "null"]}
    return None


def align_schema_to_instance(schema: dict[str, Any], instance: Any) -> dict[str, Any]:
    """对齐 OpenAPI 与真实 JSON：Hutool Tree.name 是 CharSequence（schema=object），实际是字符串。"""
    if not isinstance(schema, dict) or instance is None:
        return schema if isinstance(schema, dict) else schema

    widened = _widen_scalar(schema, instance)
    if widened is not None:
        return widened

    props = schema.get("properties") if isinstance(schema.get("properties"), dict) else None
    if isinstance(instance, dict):
        new_props = dict(props or {})
        if props:
            for k, v in props.items():
                if k in instance and isinstance(v, dict):
                    new_props[k] = align_schema_to_instance(v, instance[k])
        out = {**schema, "additionalProperties": True}
        if new_props:
            out["properties"] = new_props
        return out

    types = _types_of(schema)
    if isinstance(instance, list):
        sample = instance[0] if instance else None
        if "array" in types:
            items = schema.get("items")
            if isinstance(items, dict) and sample is not None:
                return {**schema, "items": align_schema_to_instance(items, sample)}
            return schema
        ap = schema.get("additionalProperties")
        if props:
            item = {k: v for k, v in schema.items() if k not in {"type", "additionalProperties"}}
            item["type"] = ["object", "null"]
            item["additionalProperties"] = True
            if isinstance(sample, dict):
                item = align_schema_to_instance(item, sample)
        elif isinstance(ap, dict):
            item = align_schema_to_instance(ap, sample)
        else:
            item = {"type": ["object", "null"], "additionalProperties": True}
        return {"type": ["array", "null"], "items": item}
    return schema


def validate_against_schema(
    openapi: dict[str, Any],
    registry: Registry | None,
    schema: dict[str, Any],
    instance: Any,
) -> str | None:
    _ = registry
    try:
        resolved = loosen_schema(resolve_ref(openapi, schema))
        if not isinstance(resolved, dict):
            return None
        resolved = align_schema_to_instance(resolved, instance)
        coerced = coerce_instance(resolved, instance)
        validator = Draft202012Validator(resolved)
        errors = sorted(validator.iter_errors(coerced), key=lambda e: list(e.path))
        if not errors:
            return None
        err = errors[0]
        path = ".".join(str(p) for p in err.path) or "$"
        msg = err.message.encode("ascii", "backslashreplace").decode("ascii")
        return f"{path}: {msg[:180]}"
    except Exception as exc:  # noqa: BLE001
        return f"validator_error: {exc}"


def iter_operations(openapi: dict[str, Any]) -> list[dict[str, Any]]:
    out: list[dict[str, Any]] = []
    paths = openapi.get("paths") or {}
    for path, item in paths.items():
        if not isinstance(item, dict):
            continue
        for method, op in item.items():
            m = str(method).upper()
            if m not in {"GET", "POST", "PUT", "PATCH", "DELETE"}:
                continue
            if not isinstance(op, dict):
                continue
            out.append(
                {
                    "method": m,
                    "path": path,
                    "operation": op,
                    "operationId": op.get("operationId") or f"{m} {path}",
                }
            )
    return out
