-- sys_codegen_plan / sys_codegen_field generic field migration (PostgreSQL)
-- Author: Charlie

ALTER TABLE sys_codegen_plan RENAME COLUMN main_table TO table_name;
ALTER TABLE sys_codegen_plan RENAME COLUMN main_pk TO pk_column;
ALTER TABLE sys_codegen_plan RENAME COLUMN main_entity_name TO entity_name;
ALTER TABLE sys_codegen_plan RENAME COLUMN main_module_path TO module_path;
ALTER TABLE sys_codegen_plan RENAME COLUMN main_business_name TO business_name;

ALTER TABLE sys_codegen_field RENAME COLUMN column_comment TO label;
ALTER TABLE sys_codegen_field RENAME COLUMN data_type TO value_type;
ALTER TABLE sys_codegen_field RENAME COLUMN frontend_type TO ui_type;
ALTER TABLE sys_codegen_field RENAME COLUMN form_widget TO widget;
ALTER TABLE sys_codegen_field RENAME COLUMN show_in_table TO in_table;
ALTER TABLE sys_codegen_field RENAME COLUMN show_in_form TO in_form;
ALTER TABLE sys_codegen_field RENAME COLUMN show_in_detail TO in_detail;
ALTER TABLE sys_codegen_field RENAME COLUMN show_in_query TO in_query;
ALTER TABLE sys_codegen_field RENAME COLUMN is_primary_key TO primary_key;
ALTER TABLE sys_codegen_field RENAME COLUMN is_required TO required;
ALTER TABLE sys_codegen_field RENAME COLUMN is_unique TO unique_flag;
ALTER TABLE sys_codegen_field RENAME COLUMN is_nullable TO nullable;

COMMENT ON COLUMN sys_codegen_plan.table_name IS '主表名';
COMMENT ON COLUMN sys_codegen_plan.pk_column IS '主表主键';
COMMENT ON COLUMN sys_codegen_plan.entity_name IS '主实体类名';
COMMENT ON COLUMN sys_codegen_plan.module_path IS '后端模块路径';
COMMENT ON COLUMN sys_codegen_plan.business_name IS '主业务名称';
COMMENT ON COLUMN sys_codegen_field.label IS '字段展示标签';
COMMENT ON COLUMN sys_codegen_field.value_type IS '语义值类型';
COMMENT ON COLUMN sys_codegen_field.ui_type IS 'UI 类型';
COMMENT ON COLUMN sys_codegen_field.widget IS '表单控件';
COMMENT ON COLUMN sys_codegen_field.in_table IS '表格显示';
COMMENT ON COLUMN sys_codegen_field.in_form IS '表单显示';
COMMENT ON COLUMN sys_codegen_field.in_detail IS '详情显示';
COMMENT ON COLUMN sys_codegen_field.in_query IS '查询显示';
COMMENT ON COLUMN sys_codegen_field.primary_key IS '是否主键';
COMMENT ON COLUMN sys_codegen_field.required IS '是否必填';
COMMENT ON COLUMN sys_codegen_field.unique_flag IS '是否唯一';
COMMENT ON COLUMN sys_codegen_field.nullable IS '是否可空';
