"""Bulk-rename codegen model fields in source, templates, frontend, and SQL dumps."""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
REPO = ROOT.parent

JAVA_REPLACEMENTS = [
    ("getMainTable()", "getTableName()"),
    ("getMainPk()", "getPkColumn()"),
    ("getMainEntityName()", "getEntityName()"),
    ("getMainModulePath()", "getModulePath()"),
    ("getMainBusinessName()", "getBusinessName()"),
    ("setMainTable(", "setTableName("),
    ("setMainPk(", "setPkColumn("),
    ("setMainEntityName(", "setEntityName("),
    ("setMainModulePath(", "setModulePath("),
    ("setMainBusinessName(", "setBusinessName("),
    ("getColumnComment()", "getLabel()"),
    ("setColumnComment(", "setLabel("),
    ("getDataType()", "getValueType()"),
    ("setDataType(", "setValueType("),
    ("getFrontendType()", "getUiType()"),
    ("setFrontendType(", "setUiType("),
    ("getFormWidget()", "getWidget()"),
    ("setFormWidget(", "setWidget("),
    ("getShowInTable()", "getInTable()"),
    ("setShowInTable(", "setInTable("),
    ("getShowInForm()", "getInForm()"),
    ("setShowInForm(", "setInForm("),
    ("getShowInDetail()", "getInDetail()"),
    ("setShowInDetail(", "setInDetail("),
    ("getShowInQuery()", "getInQuery()"),
    ("setShowInQuery(", "setInQuery("),
    ("getIsPrimaryKey()", "getPrimaryKey()"),
    ("setIsPrimaryKey(", "setPrimaryKey("),
    ("getIsRequired()", "getRequired()"),
    ("setIsRequired(", "setRequired("),
    ("getIsUnique()", "getUniqueFlag()"),
    ("setIsUnique(", "setUniqueFlag("),
    ("getIsNullable()", "getNullable()"),
    ("setIsNullable(", "setNullable("),
    ('put("mainEntityName"', 'put("entityName"'),
    ('put("mainBusinessName"', 'put("businessName"'),
    ('get("mainEntityName")', 'get("entityName")'),
    ('get("mainBusinessName")', 'get("businessName")'),
    ('model.put("mainEntityName"', 'model.put("entityName"'),
    ('planMap.put("main_table"', 'planMap.put("table_name"'),
    ('planMap.put("main_pk"', 'planMap.put("pk_column"'),
    ('planMap.put("main_entity_name"', 'planMap.put("entity_name"'),
    ('planMap.put("main_module_path"', 'planMap.put("module_path"'),
    ('planMap.put("main_business_name"', 'planMap.put("business_name"'),
    ('ctx.put("mainEntityName"', 'ctx.put("entityName"'),
    ('ctx.put("mainBusinessName"', 'ctx.put("businessName"'),
    ("CodegenNaming.packageFromModulePath(plan.getMainModulePath())", "CodegenNaming.packageFromModulePath(plan.getModulePath())"),
    ("plan.getMainModulePath()", "plan.getModulePath()"),
    ("plan.getMainEntityName()", "plan.getEntityName()"),
    ("plan.getMainTable()", "plan.getTableName()"),
    ("plan.getMainPk()", "plan.getPkColumn()"),
    ("plan.getMainBusinessName()", "plan.getBusinessName()"),
    ("field.getColumnComment()", "field.getLabel()"),
    ('jf.put("showInQuery"', 'jf.put("inQuery"'),
    ('jf.put("showInForm"', 'jf.put("inForm"'),
    ('jf.put("isRequired"', 'jf.put("required"'),
    ("Boolean.TRUE.equals(field.getShowInQuery())", "Boolean.TRUE.equals(field.getInQuery())"),
    ("Boolean.TRUE.equals(field.getShowInForm())", "Boolean.TRUE.equals(field.getInForm())"),
    ("Boolean.TRUE.equals(field.getIsRequired())", "Boolean.TRUE.equals(field.getRequired())"),
    ("Boolean.TRUE.equals(field.getShowInTable())", "Boolean.TRUE.equals(field.getInTable())"),
    ("Boolean.TRUE.equals(field.getShowInDetail())", "Boolean.TRUE.equals(field.getShowInDetail())"),
]

TEMPLATE_REPLACEMENTS = [
    ("field.data_type", "field.value_type"),
    ("field.form_widget", "field.widget"),
    ("field.is_required", "field.required"),
    ("field.is_bool", "field.is_bool"),  # noop anchor
]

FRONTEND_REPLACEMENTS = [
    ("main_table", "table_name"),
    ("main_pk", "pk_column"),
    ("main_entity_name", "entity_name"),
    ("main_module_path", "module_path"),
    ("main_business_name", "business_name"),
    ("column_comment", "label"),
    ("data_type", "value_type"),
    ("frontend_type", "ui_type"),
    ("form_widget", "widget"),
    ("show_in_table", "in_table"),
    ("show_in_form", "in_form"),
    ("show_in_detail", "in_detail"),
    ("show_in_query", "in_query"),
    ("is_primary_key", "primary_key"),
    ("is_required", "required"),
    ("is_unique", "unique_flag"),
    ("is_nullable", "nullable"),
]

SQL_REPLACEMENTS = [
    ("main_table", "table_name"),
    ("main_pk", "pk_column"),
    ("main_entity_name", "entity_name"),
    ("main_module_path", "module_path"),
    ("main_business_name", "business_name"),
    ("column_comment", "label"),
    ("data_type", "value_type"),
    ("frontend_type", "ui_type"),
    ("form_widget", "widget"),
    ("show_in_table", "in_table"),
    ("show_in_form", "in_form"),
    ("show_in_detail", "in_detail"),
    ("show_in_query", "in_query"),
    ("is_primary_key", "primary_key"),
    ("is_required", "required"),
    ("is_unique", "unique_flag"),
    ("is_nullable", "nullable"),
]


def apply_replacements(text: str, pairs: list[tuple[str, str]]) -> str:
    for old, new in pairs:
        text = text.replace(old, new)
    return text


def patch_codegen_template_engine(text: str) -> str:
    text = apply_replacements(text, JAVA_REPLACEMENTS)
    text = text.replace('String dataType = field.getValueType()', 'String valueType = field.getValueType()')
    text = text.replace('== null ? "str" : field.getDataType()', '== null ? "str" : field.getValueType()')
    text = text.replace('"datetime".equals(field.getFormWidget()) || "datetime".equals(dataType)',
                        '"datetime".equals(field.getWidget()) || "datetime".equals(valueType)')
    text = text.replace('"dict".equals(dataType)', '"dict".equals(valueType)')
    text = text.replace('"bool".equals(dataType)', '"bool".equals(valueType)')
    text = text.replace('fc.put("data_type", dataType)', 'fc.put("value_type", valueType)')
    text = text.replace('fc.put("form_widget", field.getFormWidget())', 'fc.put("widget", field.getWidget())')
    text = text.replace('field.getColumnComment()', 'field.getLabel()')
    text = text.replace(
        'StringUtils.hasText(field.getColumnComment()) ? field.getColumnComment() : field.getColumnName()',
        'StringUtils.hasText(field.getLabel()) ? field.getLabel() : field.getColumnName()',
    )
    text = text.replace('field.getDataType()', 'field.getValueType()')
    text = text.replace('DbTypeMapper.toJavaType(field.getDataType())', 'DbTypeMapper.toJavaType(field.getValueType())')
    text = text.replace('Boolean.TRUE.equals(field.getShowInDetail())', 'Boolean.TRUE.equals(field.getInDetail())')
    text = text.replace('Boolean.TRUE.equals(field.getShowInQuery())', 'Boolean.TRUE.equals(field.getInQuery())')
    text = text.replace('Boolean.TRUE.equals(field.getShowInForm())', 'Boolean.TRUE.equals(field.getInForm())')
    text = text.replace('isFormField(SysCodegenField field)', 'isFormField(SysCodegenField field)')
    text = text.replace('Boolean.TRUE.equals(field.getShowInForm())', 'Boolean.TRUE.equals(field.getInForm())')
    text = text.replace('entity.put("has_form_int", formFields.stream().anyMatch(f -> "int".equals(f.get("data_type"))))',
                        'entity.put("has_form_int", formFields.stream().anyMatch(f -> "int".equals(f.get("value_type"))))')
    text = text.replace('entity.put("has_form_float", formFields.stream().anyMatch(f -> "float".equals(f.get("data_type"))))',
                        'entity.put("has_form_float", formFields.stream().anyMatch(f -> "float".equals(f.get("value_type"))))')
    text = text.replace('fc.put("show_in_table", field.getShowInTable())', 'fc.put("in_table", field.getInTable())')
    text = text.replace('fc.put("show_in_form", field.getShowInForm())', 'fc.put("in_form", field.getInForm())')
    text = text.replace('fc.put("show_in_detail", field.getShowInDetail())', 'fc.put("in_detail", field.getInDetail())')
    text = text.replace('fc.put("show_in_query", field.getShowInQuery())', 'fc.put("in_query", field.getInQuery())')
    text = text.replace('fc.put("is_primary_key", field.getIsPrimaryKey())', 'fc.put("primary_key", field.getPrimaryKey())')
    text = text.replace('fc.put("is_required", field.getIsRequired())', 'fc.put("required", field.getRequired())')
    text = text.replace('fc.put("is_nullable", field.getIsNullable())', 'fc.put("nullable", field.getNullable())')
    text = text.replace('SysCodegenField field, boolean isDatetime, boolean isJson, boolean isBool, String dataType)',
                        'SysCodegenField field, boolean isDatetime, boolean isJson, boolean isBool, String valueType)')
    text = text.replace('vueDefault(field, isDatetime, isJson, isBool, dataType)', 'vueDefault(field, isDatetime, isJson, isBool, valueType)')
    text = text.replace('return Boolean.TRUE.equals(field.getShowInForm())', 'return Boolean.TRUE.equals(field.getInForm())')
    return text


def main() -> None:
    engine = ROOT / "module/sys/src/main/java/github/jiangbyte/io/sys/modules/codegen/support/CodegenTemplateEngine.java"
    text = engine.read_text(encoding="utf-8")
    engine.write_text(patch_codegen_template_engine(text), encoding="utf-8")
    print("updated", engine.name)

    for ftl in (ROOT / "module/sys/src/main/resources/codegen/templates").glob("*.ftl"):
        content = ftl.read_text(encoding="utf-8")
        content = apply_replacements(content, TEMPLATE_REPLACEMENTS)
        ftl.write_text(content, encoding="utf-8")
        print("updated", ftl.name)

    vue = REPO / "hei-admin/src/views/sys/codegen/index.vue"
    content = vue.read_text(encoding="utf-8")
    content = apply_replacements(content, FRONTEND_REPLACEMENTS)
    vue.write_text(content, encoding="utf-8")
    print("updated", vue.name)

    for sql_name in ("db.postgresql.sql", "db.mysql.sql", "db.sql"):
        path = ROOT / "scripts" / sql_name
        if not path.exists():
            continue
        content = path.read_text(encoding="utf-8")
        # only touch codegen tables/comments to avoid collateral (data_type appears elsewhere)
        lines = []
        in_codegen = False
        for line in content.splitlines(keepends=True):
            if "sys_codegen_plan" in line or "sys_codegen_field" in line:
                in_codegen = True
            elif line.startswith("-- Table structure for ") and in_codegen and "sys_codegen" not in line:
                in_codegen = False
            if in_codegen or 'COMMENT ON COLUMN "public"."sys_codegen' in line:
                line = apply_replacements(line, SQL_REPLACEMENTS)
            lines.append(line)
        path.write_text("".join(lines), encoding="utf-8")
        print("updated", path.name)


if __name__ == "__main__":
    main()
