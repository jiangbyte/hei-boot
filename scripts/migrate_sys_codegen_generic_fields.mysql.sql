-- sys_codegen_plan / sys_codegen_field generic field migration (MySQL)
-- Author: Charlie

ALTER TABLE sys_codegen_plan CHANGE COLUMN main_table table_name varchar(128) NOT NULL COMMENT '主表名';
ALTER TABLE sys_codegen_plan CHANGE COLUMN main_pk pk_column varchar(128) NOT NULL COMMENT '主表主键';
ALTER TABLE sys_codegen_plan CHANGE COLUMN main_entity_name entity_name varchar(128) NOT NULL COMMENT '主实体类名';
ALTER TABLE sys_codegen_plan CHANGE COLUMN main_module_path module_path varchar(255) NOT NULL COMMENT '后端模块路径';
ALTER TABLE sys_codegen_plan CHANGE COLUMN main_business_name business_name varchar(128) NOT NULL COMMENT '主业务名称';

ALTER TABLE sys_codegen_field CHANGE COLUMN column_comment label varchar(255) NULL COMMENT '字段展示标签';
ALTER TABLE sys_codegen_field CHANGE COLUMN data_type value_type varchar(64) NOT NULL COMMENT '语义值类型';
ALTER TABLE sys_codegen_field CHANGE COLUMN frontend_type ui_type varchar(64) NOT NULL COMMENT 'UI 类型';
ALTER TABLE sys_codegen_field CHANGE COLUMN form_widget widget varchar(32) NOT NULL COMMENT '表单控件';
ALTER TABLE sys_codegen_field CHANGE COLUMN show_in_table in_table tinyint(1) NOT NULL COMMENT '表格显示';
ALTER TABLE sys_codegen_field CHANGE COLUMN show_in_form in_form tinyint(1) NOT NULL COMMENT '表单显示';
ALTER TABLE sys_codegen_field CHANGE COLUMN show_in_detail in_detail tinyint(1) NOT NULL COMMENT '详情显示';
ALTER TABLE sys_codegen_field CHANGE COLUMN show_in_query in_query tinyint(1) NOT NULL COMMENT '查询显示';
ALTER TABLE sys_codegen_field CHANGE COLUMN is_primary_key primary_key tinyint(1) NOT NULL COMMENT '是否主键';
ALTER TABLE sys_codegen_field CHANGE COLUMN is_required required tinyint(1) NOT NULL COMMENT '是否必填';
ALTER TABLE sys_codegen_field CHANGE COLUMN is_unique unique_flag tinyint(1) NOT NULL COMMENT '是否唯一';
ALTER TABLE sys_codegen_field CHANGE COLUMN is_nullable nullable tinyint(1) NOT NULL COMMENT '是否可空';
