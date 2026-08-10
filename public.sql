/*
 Navicat Premium Dump SQL

 Source Server         : dev-postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 150017 (150017)
 Source Host           : 127.0.0.1:5432
 Source Catalog        : hei_boot
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150017 (150017)
 File Encoding         : 65001

 Date: 09/08/2026 17:54:22
*/


-- ----------------------------
-- Table structure for admin_user_profile
-- ----------------------------
DROP TABLE IF EXISTS "public"."admin_user_profile";
CREATE TABLE "public"."admin_user_profile"
(
    "account_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"       varchar(64) COLLATE "pg_catalog"."default",
    "nickname"   varchar(64) COLLATE "pg_catalog"."default",
    "avatar"     text COLLATE "pg_catalog"."default",
    "signature"  text COLLATE "pg_catalog"."default",
    "phone"      varchar(32) COLLATE "pg_catalog"."default",
    "email"      varchar(128) COLLATE "pg_catalog"."default",
    "remark"     text COLLATE "pg_catalog"."default",
    "created_at" timestamptz(6) NOT NULL DEFAULT now(),
    "created_by" varchar(64) COLLATE "pg_catalog"."default",
    "updated_at" timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."admin_user_profile"."account_id" IS '账户ID';
COMMENT
ON COLUMN "public"."admin_user_profile"."name" IS '姓名';
COMMENT
ON COLUMN "public"."admin_user_profile"."nickname" IS '昵称';
COMMENT
ON COLUMN "public"."admin_user_profile"."avatar" IS '头像';
COMMENT
ON COLUMN "public"."admin_user_profile"."signature" IS '个性签名';
COMMENT
ON COLUMN "public"."admin_user_profile"."phone" IS '手机号';
COMMENT
ON COLUMN "public"."admin_user_profile"."email" IS '邮箱';
COMMENT
ON COLUMN "public"."admin_user_profile"."remark" IS '备注';
COMMENT
ON COLUMN "public"."admin_user_profile"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."admin_user_profile"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."admin_user_profile"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."admin_user_profile"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for alembic_version
-- ----------------------------
DROP TABLE IF EXISTS "public"."alembic_version";
CREATE TABLE "public"."alembic_version"
(
    "version_num" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for cg_test_activity
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_activity";
CREATE TABLE "public"."cg_test_activity"
(
    "id"               varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "code"             varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"             varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "category"         varchar(32) COLLATE "pg_catalog"."default",
    "type"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "status"           varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "cover_url"        varchar(512) COLLATE "pg_catalog"."default",
    "description"      text COLLATE "pg_catalog"."default",
    "start_at"         timestamptz(6) NOT NULL,
    "end_at"           timestamptz(6),
    "max_participants" int4                                        NOT NULL,
    "price"            numeric                                     NOT NULL,
    "is_public"        bool                                        NOT NULL,
    "need_approval"    bool                                        NOT NULL,
    "rule_config"      json                                        NOT NULL,
    "extra"            json,
    "created_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"       varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"       varchar(64) COLLATE "pg_catalog"."default",
    "owner_dept_id"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_activity"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_activity"."code" IS '活动编码';
COMMENT
ON COLUMN "public"."cg_test_activity"."name" IS '活动名称';
COMMENT
ON COLUMN "public"."cg_test_activity"."category" IS '活动分类';
COMMENT
ON COLUMN "public"."cg_test_activity"."type" IS '活动类型';
COMMENT
ON COLUMN "public"."cg_test_activity"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_activity"."cover_url" IS '封面地址';
COMMENT
ON COLUMN "public"."cg_test_activity"."description" IS '活动描述';
COMMENT
ON COLUMN "public"."cg_test_activity"."start_at" IS '开始时间';
COMMENT
ON COLUMN "public"."cg_test_activity"."end_at" IS '结束时间';
COMMENT
ON COLUMN "public"."cg_test_activity"."max_participants" IS '最大参与人数';
COMMENT
ON COLUMN "public"."cg_test_activity"."price" IS '报名费用';
COMMENT
ON COLUMN "public"."cg_test_activity"."is_public" IS '是否公开';
COMMENT
ON COLUMN "public"."cg_test_activity"."need_approval" IS '是否需要审批';
COMMENT
ON COLUMN "public"."cg_test_activity"."rule_config" IS '规则配置';
COMMENT
ON COLUMN "public"."cg_test_activity"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_activity"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_activity"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_activity"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_activity"."updated_by" IS '更新人';
COMMENT
ON COLUMN "public"."cg_test_activity"."owner_dept_id" IS '所属部门ID（数据范围）';

-- ----------------------------
-- Table structure for cg_test_catalog
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_catalog";
CREATE TABLE "public"."cg_test_catalog"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "parent_id"     varchar(64) COLLATE "pg_catalog"."default",
    "code"          varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"          varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "category"      varchar(32) COLLATE "pg_catalog"."default",
    "status"        varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "sort"          int4                                        NOT NULL,
    "is_visible"    bool                                        NOT NULL,
    "icon"          varchar(128) COLLATE "pg_catalog"."default",
    "description"   text COLLATE "pg_catalog"."default",
    "extra"         json                                        NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default",
    "owner_dept_id" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_catalog"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_catalog"."parent_id" IS '父级ID';
COMMENT
ON COLUMN "public"."cg_test_catalog"."code" IS '目录编码';
COMMENT
ON COLUMN "public"."cg_test_catalog"."name" IS '目录名称';
COMMENT
ON COLUMN "public"."cg_test_catalog"."category" IS '目录分类';
COMMENT
ON COLUMN "public"."cg_test_catalog"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_catalog"."sort" IS '排序';
COMMENT
ON COLUMN "public"."cg_test_catalog"."is_visible" IS '是否显示';
COMMENT
ON COLUMN "public"."cg_test_catalog"."icon" IS '图标';
COMMENT
ON COLUMN "public"."cg_test_catalog"."description" IS '描述';
COMMENT
ON COLUMN "public"."cg_test_catalog"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_catalog"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_catalog"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_catalog"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_catalog"."updated_by" IS '更新人';
COMMENT
ON COLUMN "public"."cg_test_catalog"."owner_dept_id" IS '所属部门ID（数据范围）';

-- ----------------------------
-- Table structure for cg_test_knowledge_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_knowledge_category";
CREATE TABLE "public"."cg_test_knowledge_category"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "parent_id"     varchar(64) COLLATE "pg_catalog"."default",
    "code"          varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"          varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "status"        varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "sort"          int4                                        NOT NULL,
    "is_visible"    bool                                        NOT NULL,
    "description"   text COLLATE "pg_catalog"."default",
    "extra"         json                                        NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default",
    "owner_dept_id" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."parent_id" IS '父级ID';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."code" IS '分类编码';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."name" IS '分类名称';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."sort" IS '排序';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."is_visible" IS '是否显示';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."description" IS '描述';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."updated_by" IS '更新人';
COMMENT
ON COLUMN "public"."cg_test_knowledge_category"."owner_dept_id" IS '所属部门ID（数据范围）';

-- ----------------------------
-- Table structure for cg_test_knowledge_doc
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_knowledge_doc";
CREATE TABLE "public"."cg_test_knowledge_doc"
(
    "id"           varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "category_id"  varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "code"         varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "title"        varchar(160) COLLATE "pg_catalog"."default" NOT NULL,
    "type"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "status"       varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "summary"      varchar(512) COLLATE "pg_catalog"."default",
    "content"      text COLLATE "pg_catalog"."default",
    "author"       varchar(64) COLLATE "pg_catalog"."default",
    "published_at" timestamptz(6),
    "view_count"   int4                                        NOT NULL,
    "sort"         int4                                        NOT NULL,
    "is_top"       bool                                        NOT NULL,
    "settings"     json                                        NOT NULL,
    "extra"        json,
    "created_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"   varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"   varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."category_id" IS '分类ID';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."code" IS '文档编码';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."title" IS '文档标题';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."type" IS '文档类型';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."summary" IS '摘要';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."content" IS '正文内容';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."author" IS '作者';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."published_at" IS '发布时间';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."view_count" IS '浏览次数';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."sort" IS '排序';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."is_top" IS '是否置顶';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."settings" IS '展示设置';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_knowledge_doc"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for cg_test_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_order";
CREATE TABLE "public"."cg_test_order"
(
    "id"             varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "order_no"       varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"           varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "customer_name"  varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "customer_phone" varchar(32) COLLATE "pg_catalog"."default",
    "status"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "type"           varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "ordered_at"     timestamptz(6) NOT NULL,
    "paid_at"        timestamptz(6),
    "total_amount"   numeric                                     NOT NULL,
    "item_count"     int4                                        NOT NULL,
    "need_invoice"   bool                                        NOT NULL,
    "invoice_config" json                                        NOT NULL,
    "remark"         text COLLATE "pg_catalog"."default",
    "extra"          json,
    "created_at"     timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"     varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"     timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"     varchar(64) COLLATE "pg_catalog"."default",
    "owner_dept_id"  varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_order"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_order"."order_no" IS '订单号';
COMMENT
ON COLUMN "public"."cg_test_order"."name" IS '订单名称';
COMMENT
ON COLUMN "public"."cg_test_order"."customer_name" IS '客户名称';
COMMENT
ON COLUMN "public"."cg_test_order"."customer_phone" IS '客户手机号';
COMMENT
ON COLUMN "public"."cg_test_order"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_order"."type" IS '订单类型';
COMMENT
ON COLUMN "public"."cg_test_order"."ordered_at" IS '下单时间';
COMMENT
ON COLUMN "public"."cg_test_order"."paid_at" IS '支付时间';
COMMENT
ON COLUMN "public"."cg_test_order"."total_amount" IS '订单金额';
COMMENT
ON COLUMN "public"."cg_test_order"."item_count" IS '商品数量';
COMMENT
ON COLUMN "public"."cg_test_order"."need_invoice" IS '是否开票';
COMMENT
ON COLUMN "public"."cg_test_order"."invoice_config" IS '发票配置';
COMMENT
ON COLUMN "public"."cg_test_order"."remark" IS '备注';
COMMENT
ON COLUMN "public"."cg_test_order"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_order"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_order"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_order"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_order"."updated_by" IS '更新人';
COMMENT
ON COLUMN "public"."cg_test_order"."owner_dept_id" IS '所属部门ID（数据范围）';

-- ----------------------------
-- Table structure for cg_test_order_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."cg_test_order_item";
CREATE TABLE "public"."cg_test_order_item"
(
    "id"          varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "order_id"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "sku_code"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"        varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
    "category"    varchar(32) COLLATE "pg_catalog"."default",
    "status"      varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "quantity"    int4                                        NOT NULL,
    "unit_price"  numeric                                     NOT NULL,
    "shipped_at"  timestamptz(6),
    "is_gift"     bool                                        NOT NULL,
    "item_config" json                                        NOT NULL,
    "remark"      text COLLATE "pg_catalog"."default",
    "extra"       json,
    "created_at"  timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"  varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"  timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"  varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."cg_test_order_item"."id" IS '主键';
COMMENT
ON COLUMN "public"."cg_test_order_item"."order_id" IS '订单ID';
COMMENT
ON COLUMN "public"."cg_test_order_item"."sku_code" IS 'SKU编码';
COMMENT
ON COLUMN "public"."cg_test_order_item"."name" IS '商品名称';
COMMENT
ON COLUMN "public"."cg_test_order_item"."category" IS '商品分类';
COMMENT
ON COLUMN "public"."cg_test_order_item"."status" IS '状态';
COMMENT
ON COLUMN "public"."cg_test_order_item"."quantity" IS '数量';
COMMENT
ON COLUMN "public"."cg_test_order_item"."unit_price" IS '单价';
COMMENT
ON COLUMN "public"."cg_test_order_item"."shipped_at" IS '发货时间';
COMMENT
ON COLUMN "public"."cg_test_order_item"."is_gift" IS '是否赠品';
COMMENT
ON COLUMN "public"."cg_test_order_item"."item_config" IS '明细配置';
COMMENT
ON COLUMN "public"."cg_test_order_item"."remark" IS '备注';
COMMENT
ON COLUMN "public"."cg_test_order_item"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."cg_test_order_item"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."cg_test_order_item"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."cg_test_order_item"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."cg_test_order_item"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for msg_feedback
-- ----------------------------
DROP TABLE IF EXISTS "public"."msg_feedback";
CREATE TABLE "public"."msg_feedback"
(
    "id"                     varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "title"                  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "content"                text COLLATE "pg_catalog"."default"         NOT NULL,
    "category"               varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "contact"                varchar(255) COLLATE "pg_catalog"."default",
    "attach_object_names"    json                                        NOT NULL,
    "status"                 varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "reply"                  text COLLATE "pg_catalog"."default",
    "replied_by"             varchar(64) COLLATE "pg_catalog"."default",
    "replied_at"             timestamptz(6),
    "submitter_account_type" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "submitter_account_id"   varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "created_at"             timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"             varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"             timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"             varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."msg_feedback"."id" IS '主键';
COMMENT
ON COLUMN "public"."msg_feedback"."title" IS '反馈标题';
COMMENT
ON COLUMN "public"."msg_feedback"."content" IS '反馈内容';
COMMENT
ON COLUMN "public"."msg_feedback"."category" IS '反馈分类';
COMMENT
ON COLUMN "public"."msg_feedback"."contact" IS '联系方式';
COMMENT
ON COLUMN "public"."msg_feedback"."attach_object_names" IS '附件 object_name 列表';
COMMENT
ON COLUMN "public"."msg_feedback"."status" IS '状态';
COMMENT
ON COLUMN "public"."msg_feedback"."reply" IS '管理员回复';
COMMENT
ON COLUMN "public"."msg_feedback"."replied_by" IS '回复人ID';
COMMENT
ON COLUMN "public"."msg_feedback"."replied_at" IS '回复时间';
COMMENT
ON COLUMN "public"."msg_feedback"."submitter_account_type" IS '提交者账户类型';
COMMENT
ON COLUMN "public"."msg_feedback"."submitter_account_id" IS '提交者账户ID';
COMMENT
ON COLUMN "public"."msg_feedback"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."msg_feedback"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."msg_feedback"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."msg_feedback"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for msg_notice
-- ----------------------------
DROP TABLE IF EXISTS "public"."msg_notice";
CREATE TABLE "public"."msg_notice"
(
    "id"                   varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "kind"                 varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "title"                varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "content"              text COLLATE "pg_catalog"."default"         NOT NULL,
    "content_type"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "category"             varchar(32) COLLATE "pg_catalog"."default",
    "severity"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_scope"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_account_types" json                                        NOT NULL,
    "target_account_ids"   json                                        NOT NULL,
    "target_dept_ids"      json                                        NOT NULL,
    "target_role_ids"      json                                        NOT NULL,
    "publish_locations"    json                                        NOT NULL,
    "is_pinned"            bool                                        NOT NULL,
    "pinned_until"         timestamptz(6),
    "sender_account_type"  varchar(32) COLLATE "pg_catalog"."default",
    "sender_account_id"    varchar(64) COLLATE "pg_catalog"."default",
    "source_type"          varchar(64) COLLATE "pg_catalog"."default",
    "source_id"            varchar(64) COLLATE "pg_catalog"."default",
    "status"               varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "publish_at"           timestamptz(6),
    "revoked_at"           timestamptz(6),
    "expire_at"            timestamptz(6),
    "view_count"           int4                                        NOT NULL,
    "extra"                json                                        NOT NULL,
    "created_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"           varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"           varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."msg_notice"."id" IS '主键';
COMMENT
ON COLUMN "public"."msg_notice"."kind" IS '类型：NOTIFICATION|ANNOUNCEMENT';
COMMENT
ON COLUMN "public"."msg_notice"."title" IS '标题';
COMMENT
ON COLUMN "public"."msg_notice"."content" IS '内容';
COMMENT
ON COLUMN "public"."msg_notice"."content_type" IS '内容格式';
COMMENT
ON COLUMN "public"."msg_notice"."category" IS '分类（通知）';
COMMENT
ON COLUMN "public"."msg_notice"."severity" IS '等级';
COMMENT
ON COLUMN "public"."msg_notice"."target_scope" IS '目标范围';
COMMENT
ON COLUMN "public"."msg_notice"."target_account_types" IS '目标账户类型列表';
COMMENT
ON COLUMN "public"."msg_notice"."target_account_ids" IS '目标账户ID列表';
COMMENT
ON COLUMN "public"."msg_notice"."target_dept_ids" IS '目标部门ID列表';
COMMENT
ON COLUMN "public"."msg_notice"."target_role_ids" IS '目标角色ID列表';
COMMENT
ON COLUMN "public"."msg_notice"."publish_locations" IS '发布位置（公告）';
COMMENT
ON COLUMN "public"."msg_notice"."is_pinned" IS '是否置顶（公告）';
COMMENT
ON COLUMN "public"."msg_notice"."pinned_until" IS '置顶截止时间';
COMMENT
ON COLUMN "public"."msg_notice"."sender_account_type" IS '发送者账户类型';
COMMENT
ON COLUMN "public"."msg_notice"."sender_account_id" IS '发送者账户ID';
COMMENT
ON COLUMN "public"."msg_notice"."source_type" IS '来源模块（通知）';
COMMENT
ON COLUMN "public"."msg_notice"."source_id" IS '来源业务ID（通知）';
COMMENT
ON COLUMN "public"."msg_notice"."status" IS '状态';
COMMENT
ON COLUMN "public"."msg_notice"."publish_at" IS '发布时间';
COMMENT
ON COLUMN "public"."msg_notice"."revoked_at" IS '撤回时间';
COMMENT
ON COLUMN "public"."msg_notice"."expire_at" IS '过期时间（公告）';
COMMENT
ON COLUMN "public"."msg_notice"."view_count" IS '查看次数';
COMMENT
ON COLUMN "public"."msg_notice"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."msg_notice"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."msg_notice"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."msg_notice"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."msg_notice"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for msg_notice_read
-- ----------------------------
DROP TABLE IF EXISTS "public"."msg_notice_read";
CREATE TABLE "public"."msg_notice_read"
(
    "id"           varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "notice_id"    varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "account_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "account_id"   varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "read_at"      timestamptz(6) NOT NULL DEFAULT now()
)
;
COMMENT
ON COLUMN "public"."msg_notice_read"."id" IS '主键';
COMMENT
ON COLUMN "public"."msg_notice_read"."notice_id" IS '消息ID';
COMMENT
ON COLUMN "public"."msg_notice_read"."account_type" IS '账户类型';
COMMENT
ON COLUMN "public"."msg_notice_read"."account_id" IS '账户ID';
COMMENT
ON COLUMN "public"."msg_notice_read"."read_at" IS '阅读时间';

-- ----------------------------
-- Table structure for portal_user_profile
-- ----------------------------
DROP TABLE IF EXISTS "public"."portal_user_profile";
CREATE TABLE "public"."portal_user_profile"
(
    "account_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"       varchar(64) COLLATE "pg_catalog"."default",
    "nickname"   varchar(64) COLLATE "pg_catalog"."default",
    "avatar"     text COLLATE "pg_catalog"."default",
    "signature"  text COLLATE "pg_catalog"."default",
    "phone"      varchar(32) COLLATE "pg_catalog"."default",
    "email"      varchar(128) COLLATE "pg_catalog"."default",
    "created_at" timestamptz(6) NOT NULL DEFAULT now(),
    "created_by" varchar(64) COLLATE "pg_catalog"."default",
    "updated_at" timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."portal_user_profile"."account_id" IS '账户ID';
COMMENT
ON COLUMN "public"."portal_user_profile"."name" IS '姓名';
COMMENT
ON COLUMN "public"."portal_user_profile"."nickname" IS '昵称';
COMMENT
ON COLUMN "public"."portal_user_profile"."avatar" IS '头像';
COMMENT
ON COLUMN "public"."portal_user_profile"."signature" IS '个性签名';
COMMENT
ON COLUMN "public"."portal_user_profile"."phone" IS '手机号';
COMMENT
ON COLUMN "public"."portal_user_profile"."email" IS '邮箱';
COMMENT
ON COLUMN "public"."portal_user_profile"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."portal_user_profile"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."portal_user_profile"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."portal_user_profile"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_account";
CREATE TABLE "public"."sys_account"
(
    "id"                   varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "password_hash"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "account_type"         varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "account_status"       varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "cancelled_at"         timestamptz(6),
    "cancelled_by"         varchar(64) COLLATE "pg_catalog"."default",
    "cancel_reason"        text COLLATE "pg_catalog"."default",
    "cancel_notify_email"  varchar(128) COLLATE "pg_catalog"."default",
    "cancel_notify_phone"  varchar(32) COLLATE "pg_catalog"."default",
    "last_login_ip"        varchar(64) COLLATE "pg_catalog"."default",
    "last_login_address"   varchar(255) COLLATE "pg_catalog"."default",
    "last_login_time"      timestamptz(6),
    "last_login_device"    text COLLATE "pg_catalog"."default",
    "latest_login_ip"      varchar(64) COLLATE "pg_catalog"."default",
    "latest_login_address" varchar(255) COLLATE "pg_catalog"."default",
    "latest_login_time"    timestamptz(6),
    "latest_login_device"  text COLLATE "pg_catalog"."default",
    "created_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"           varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"           varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_account"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_account"."password_hash" IS '密码哈希';
COMMENT
ON COLUMN "public"."sys_account"."account_type" IS '账户类型';
COMMENT
ON COLUMN "public"."sys_account"."account_status" IS '账户状态';
COMMENT
ON COLUMN "public"."sys_account"."cancelled_at" IS '注销时间';
COMMENT
ON COLUMN "public"."sys_account"."cancelled_by" IS '注销人';
COMMENT
ON COLUMN "public"."sys_account"."cancel_reason" IS '注销原因';
COMMENT
ON COLUMN "public"."sys_account"."cancel_notify_email" IS '注销通知邮箱（清理身份前快照）';
COMMENT
ON COLUMN "public"."sys_account"."cancel_notify_phone" IS '注销通知手机号（清理身份前快照）';
COMMENT
ON COLUMN "public"."sys_account"."last_login_ip" IS '上次登录IP';
COMMENT
ON COLUMN "public"."sys_account"."last_login_address" IS '上次登录地点';
COMMENT
ON COLUMN "public"."sys_account"."last_login_time" IS '上次登录时间';
COMMENT
ON COLUMN "public"."sys_account"."last_login_device" IS '上次登录设备';
COMMENT
ON COLUMN "public"."sys_account"."latest_login_ip" IS '最新登录IP';
COMMENT
ON COLUMN "public"."sys_account"."latest_login_address" IS '最新登录地点';
COMMENT
ON COLUMN "public"."sys_account"."latest_login_time" IS '最新登录时间';
COMMENT
ON COLUMN "public"."sys_account"."latest_login_device" IS '最新登录设备';
COMMENT
ON COLUMN "public"."sys_account"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_account"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_account"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_account"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_account_identity
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_account_identity";
CREATE TABLE "public"."sys_account_identity"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "account_id"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "identity_type" varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "identifier"    varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "verified"      bool                                        NOT NULL,
    "is_primary"    bool                                        NOT NULL,
    "bind_status"   varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_account_identity"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_account_identity"."account_id" IS '账户ID';
COMMENT
ON COLUMN "public"."sys_account_identity"."identity_type" IS '登录标识类型';
COMMENT
ON COLUMN "public"."sys_account_identity"."identifier" IS '登录标识';
COMMENT
ON COLUMN "public"."sys_account_identity"."verified" IS '是否已验证';
COMMENT
ON COLUMN "public"."sys_account_identity"."is_primary" IS '是否主标识';
COMMENT
ON COLUMN "public"."sys_account_identity"."bind_status" IS '绑定状态';
COMMENT
ON COLUMN "public"."sys_account_identity"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_account_identity"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_account_identity"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_account_identity"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_account_password_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_account_password_history";
CREATE TABLE "public"."sys_account_password_history"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "account_id"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "password_hash" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "changed_by"    varchar(64) COLLATE "pg_catalog"."default",
    "change_reason" varchar(64) COLLATE "pg_catalog"."default",
    "created_at"    timestamptz(6) NOT NULL DEFAULT now()
)
;
COMMENT
ON COLUMN "public"."sys_account_password_history"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_account_password_history"."account_id" IS '账户ID';
COMMENT
ON COLUMN "public"."sys_account_password_history"."password_hash" IS '密码哈希';
COMMENT
ON COLUMN "public"."sys_account_password_history"."changed_by" IS '变更人（账户ID或系统）';
COMMENT
ON COLUMN "public"."sys_account_password_history"."change_reason" IS '变更原因: register / admin_reset / self_reset / password_expired';
COMMENT
ON COLUMN "public"."sys_account_password_history"."created_at" IS '变更时间';

-- ----------------------------
-- Table structure for sys_alert_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_alert_log";
CREATE TABLE "public"."sys_alert_log"
(
    "id"           varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "rule_name"    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "severity"     varchar(16) COLLATE "pg_catalog"."default"  NOT NULL,
    "summary"      varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "details"      json,
    "notified_via" varchar(64) COLLATE "pg_catalog"."default",
    "created_at"   timestamptz(6) NOT NULL DEFAULT now()
)
;
COMMENT
ON COLUMN "public"."sys_alert_log"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_alert_log"."rule_name" IS '规则名称';
COMMENT
ON COLUMN "public"."sys_alert_log"."severity" IS '严重级别: INFO/WARNING/CRITICAL';
COMMENT
ON COLUMN "public"."sys_alert_log"."summary" IS '告警摘要';
COMMENT
ON COLUMN "public"."sys_alert_log"."details" IS '告警详情（JSON）';
COMMENT
ON COLUMN "public"."sys_alert_log"."notified_via" IS '通知方式: email/webhook';
COMMENT
ON COLUMN "public"."sys_alert_log"."created_at" IS '通知时间';

-- ----------------------------
-- Table structure for sys_banner
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_banner";
CREATE TABLE "public"."sys_banner"
(
    "id"                   varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "title"                varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "image"                varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
    "url"                  varchar(500) COLLATE "pg_catalog"."default",
    "link_type"            varchar(16) COLLATE "pg_catalog"."default"  NOT NULL,
    "summary"              varchar(500) COLLATE "pg_catalog"."default",
    "description"          text COLLATE "pg_catalog"."default",
    "category"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "type"                 varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "position"             varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_account_types" json                                        NOT NULL,
    "sort"                 int4                                        NOT NULL,
    "interaction_count"    int8                                        NOT NULL,
    "status"               varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "start_at"             timestamptz(6),
    "end_at"               timestamptz(6),
    "created_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"           varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"           timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"           varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_banner"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_banner"."title" IS '标题';
COMMENT
ON COLUMN "public"."sys_banner"."image" IS '图片 object_name（读取时由服务层解析为 URL）';
COMMENT
ON COLUMN "public"."sys_banner"."url" IS '跳转地址';
COMMENT
ON COLUMN "public"."sys_banner"."link_type" IS '链接类型：展示图链接类型，对应 BANNER_LINK_TYPE 字典组子项 value。';
COMMENT
ON COLUMN "public"."sys_banner"."summary" IS '摘要';
COMMENT
ON COLUMN "public"."sys_banner"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_banner"."category" IS '分类：展示图分类，对应 BANNER_CATEGORY 字典组子项 value（可扩展，接口以 str 为准）。';
COMMENT
ON COLUMN "public"."sys_banner"."type" IS '类型：展示图类型，对应 BANNER_TYPE 字典组子项 value（可扩展，接口以 str 为准）。';
COMMENT
ON COLUMN "public"."sys_banner"."position" IS '显示位置：展示图显示位置，对应 BANNER_POSITION 字典组子项 value（可扩展，接口以 str 为准）。';
COMMENT
ON COLUMN "public"."sys_banner"."target_account_types" IS '目标账户类型列表（AccountType：ADMIN/PORTAL）';
COMMENT
ON COLUMN "public"."sys_banner"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_banner"."interaction_count" IS '交互次数';
COMMENT
ON COLUMN "public"."sys_banner"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_banner"."start_at" IS '开始展示时间';
COMMENT
ON COLUMN "public"."sys_banner"."end_at" IS '结束展示时间';
COMMENT
ON COLUMN "public"."sys_banner"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_banner"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_banner"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_banner"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_client_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_client_module";
CREATE TABLE "public"."sys_client_module"
(
    "id"           varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"         varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "code"         varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "account_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "icon"         varchar(255) COLLATE "pg_catalog"."default",
    "color"        varchar(32) COLLATE "pg_catalog"."default",
    "sort"         int4                                       NOT NULL,
    "status"       varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "description"  text COLLATE "pg_catalog"."default",
    "extra"        json                                       NOT NULL,
    "created_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"   varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"   varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_client_module"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_client_module"."name" IS '模块名称';
COMMENT
ON COLUMN "public"."sys_client_module"."code" IS '模块编码';
COMMENT
ON COLUMN "public"."sys_client_module"."account_type" IS '账户体系';
COMMENT
ON COLUMN "public"."sys_client_module"."icon" IS '图标';
COMMENT
ON COLUMN "public"."sys_client_module"."color" IS '颜色';
COMMENT
ON COLUMN "public"."sys_client_module"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_client_module"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_client_module"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_client_module"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_client_module"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_client_module"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_client_module"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_client_module"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_client_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_client_resource";
CREATE TABLE "public"."sys_client_resource"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"     varchar(64) COLLATE "pg_catalog"."default",
    "code"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "resource_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "module_id"     varchar(64) COLLATE "pg_catalog"."default",
    "path"          varchar(255) COLLATE "pg_catalog"."default",
    "component"     varchar(255) COLLATE "pg_catalog"."default",
    "redirect"      varchar(255) COLLATE "pg_catalog"."default",
    "icon"          varchar(255) COLLATE "pg_catalog"."default",
    "color"         varchar(32) COLLATE "pg_catalog"."default",
    "href"          varchar(255) COLLATE "pg_catalog"."default",
    "sort"          int4                                       NOT NULL,
    "is_visible"    bool                                       NOT NULL,
    "is_cache"      bool                                       NOT NULL,
    "is_affix"      bool                                       NOT NULL,
    "status"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "description"   text COLLATE "pg_catalog"."default",
    "layout"        varchar(255) COLLATE "pg_catalog"."default",
    "extra"         json                                       NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_client_resource"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_client_resource"."parent_id" IS '父资源ID';
COMMENT
ON COLUMN "public"."sys_client_resource"."code" IS '资源编码';
COMMENT
ON COLUMN "public"."sys_client_resource"."name" IS '资源名称';
COMMENT
ON COLUMN "public"."sys_client_resource"."resource_type" IS '资源类型';
COMMENT
ON COLUMN "public"."sys_client_resource"."module_id" IS '所属客户端模块ID';
COMMENT
ON COLUMN "public"."sys_client_resource"."path" IS '路由路径';
COMMENT
ON COLUMN "public"."sys_client_resource"."component" IS '前端组件';
COMMENT
ON COLUMN "public"."sys_client_resource"."redirect" IS '重定向地址';
COMMENT
ON COLUMN "public"."sys_client_resource"."icon" IS '图标';
COMMENT
ON COLUMN "public"."sys_client_resource"."color" IS '颜色';
COMMENT
ON COLUMN "public"."sys_client_resource"."href" IS '外链地址';
COMMENT
ON COLUMN "public"."sys_client_resource"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_client_resource"."is_visible" IS '是否可见';
COMMENT
ON COLUMN "public"."sys_client_resource"."is_cache" IS '是否缓存';
COMMENT
ON COLUMN "public"."sys_client_resource"."is_affix" IS '是否固定标签';
COMMENT
ON COLUMN "public"."sys_client_resource"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_client_resource"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_client_resource"."layout" IS '布局类型';
COMMENT
ON COLUMN "public"."sys_client_resource"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_client_resource"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_client_resource"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_client_resource"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_client_resource"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_codegen_field
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_codegen_field";
CREATE TABLE "public"."sys_codegen_field"
(
    "id"              varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "plan_id"         varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "table_role"      varchar(16) COLLATE "pg_catalog"."default"  NOT NULL,
    "column_name"     varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "column_comment"  varchar(255) COLLATE "pg_catalog"."default",
    "db_type"         varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "python_type"     varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "typescript_type" varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "form_widget"     varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "dict_code"       varchar(128) COLLATE "pg_catalog"."default",
    "query_operator"  varchar(32) COLLATE "pg_catalog"."default",
    "show_in_table"   bool                                        NOT NULL,
    "show_in_form"    bool                                        NOT NULL,
    "show_in_detail"  bool                                        NOT NULL,
    "show_in_query"   bool                                        NOT NULL,
    "is_primary_key"  bool                                        NOT NULL,
    "is_required"     bool                                        NOT NULL,
    "is_unique"       bool                                        NOT NULL,
    "is_nullable"     bool                                        NOT NULL,
    "max_length"      int4,
    "sort"            int4                                        NOT NULL,
    "created_at"      timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"      varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"      timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"      varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_codegen_field"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_codegen_field"."plan_id" IS '方案ID';
COMMENT
ON COLUMN "public"."sys_codegen_field"."table_role" IS '表角色';
COMMENT
ON COLUMN "public"."sys_codegen_field"."column_name" IS '字段名';
COMMENT
ON COLUMN "public"."sys_codegen_field"."column_comment" IS '字段注释';
COMMENT
ON COLUMN "public"."sys_codegen_field"."db_type" IS '数据库类型';
COMMENT
ON COLUMN "public"."sys_codegen_field"."python_type" IS 'Python类型';
COMMENT
ON COLUMN "public"."sys_codegen_field"."typescript_type" IS 'TypeScript类型';
COMMENT
ON COLUMN "public"."sys_codegen_field"."form_widget" IS '表单控件';
COMMENT
ON COLUMN "public"."sys_codegen_field"."dict_code" IS '字典编码';
COMMENT
ON COLUMN "public"."sys_codegen_field"."query_operator" IS '查询方式';
COMMENT
ON COLUMN "public"."sys_codegen_field"."show_in_table" IS '表格显示';
COMMENT
ON COLUMN "public"."sys_codegen_field"."show_in_form" IS '表单显示';
COMMENT
ON COLUMN "public"."sys_codegen_field"."show_in_detail" IS '详情显示';
COMMENT
ON COLUMN "public"."sys_codegen_field"."show_in_query" IS '查询显示';
COMMENT
ON COLUMN "public"."sys_codegen_field"."is_primary_key" IS '是否主键';
COMMENT
ON COLUMN "public"."sys_codegen_field"."is_required" IS '是否必填';
COMMENT
ON COLUMN "public"."sys_codegen_field"."is_unique" IS '是否唯一';
COMMENT
ON COLUMN "public"."sys_codegen_field"."is_nullable" IS '是否可空';
COMMENT
ON COLUMN "public"."sys_codegen_field"."max_length" IS '最大长度';
COMMENT
ON COLUMN "public"."sys_codegen_field"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_codegen_field"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_codegen_field"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_codegen_field"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_codegen_field"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_codegen_plan
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_codegen_plan";
CREATE TABLE "public"."sys_codegen_plan"
(
    "id"                 varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "name"               varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "gen_type"           varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "author"             varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "description"        text COLLATE "pg_catalog"."default",
    "main_table"         varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "main_pk"            varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "main_entity_name"   varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "main_module_path"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "main_business_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "api_prefix"         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "permission_prefix"  varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "resource_module_id" varchar(64) COLLATE "pg_catalog"."default",
    "parent_resource_id" varchar(64) COLLATE "pg_catalog"."default",
    "menu_name"          varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "menu_path"          varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "component_path"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "icon"               varchar(255) COLLATE "pg_catalog"."default",
    "sort"               int4                                        NOT NULL,
    "tree_parent_field"  varchar(128) COLLATE "pg_catalog"."default",
    "tree_label_field"   varchar(128) COLLATE "pg_catalog"."default",
    "sub_table"          varchar(128) COLLATE "pg_catalog"."default",
    "sub_pk"             varchar(128) COLLATE "pg_catalog"."default",
    "sub_foreign_key"    varchar(128) COLLATE "pg_catalog"."default",
    "sub_entity_name"    varchar(128) COLLATE "pg_catalog"."default",
    "sub_business_name"  varchar(128) COLLATE "pg_catalog"."default",
    "created_at"         timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"         varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"         timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"         varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_codegen_plan"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."name" IS '方案名称';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."gen_type" IS '生成类型';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."author" IS '作者';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."main_table" IS '主表名';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."main_pk" IS '主表主键';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."main_entity_name" IS '主实体类名';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."main_module_path" IS '后端模块路径';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."main_business_name" IS '主业务名称';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."api_prefix" IS '接口前缀';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."permission_prefix" IS '权限前缀';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."resource_module_id" IS '资源模块ID';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."parent_resource_id" IS '父资源ID';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."menu_name" IS '菜单名称';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."menu_path" IS '菜单路径';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."component_path" IS '组件路径';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."icon" IS '菜单图标';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."tree_parent_field" IS '树父级字段';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."tree_label_field" IS '树展示字段';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sub_table" IS '子表名';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sub_pk" IS '子表主键';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sub_foreign_key" IS '子表外键';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sub_entity_name" IS '子实体类名';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."sub_business_name" IS '子业务名称';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_codegen_plan"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config"
(
    "id"           varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "config_key"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "config_value" text COLLATE "pg_catalog"."default",
    "category"     varchar(255) COLLATE "pg_catalog"."default",
    "remark"       varchar(255) COLLATE "pg_catalog"."default",
    "sort_code"    int4                                        NOT NULL,
    "value_type"   varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "label"        varchar(128) COLLATE "pg_catalog"."default",
    "scope"        varchar(32) COLLATE "pg_catalog"."default",
    "scene"        varchar(64) COLLATE "pg_catalog"."default",
    "is_builtin"   bool                                        NOT NULL,
    "ext_json"     json                                        NOT NULL,
    "created_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"   varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"   timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"   varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_config"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_config"."config_key" IS '配置键';
COMMENT
ON COLUMN "public"."sys_config"."config_value" IS '配置值';
COMMENT
ON COLUMN "public"."sys_config"."category" IS '分类';
COMMENT
ON COLUMN "public"."sys_config"."remark" IS '备注';
COMMENT
ON COLUMN "public"."sys_config"."sort_code" IS '排序码';
COMMENT
ON COLUMN "public"."sys_config"."value_type" IS '值类型: STRING|JSON|BOOL|NUMBER';
COMMENT
ON COLUMN "public"."sys_config"."label" IS '展示名';
COMMENT
ON COLUMN "public"."sys_config"."scope" IS '作用域账户类型';
COMMENT
ON COLUMN "public"."sys_config"."scene" IS '场景编码';
COMMENT
ON COLUMN "public"."sys_config"."is_builtin" IS '是否内置（不可删除）';
COMMENT
ON COLUMN "public"."sys_config"."ext_json" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_config"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_config"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_config"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_config"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept"
(
    "id"               varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"        varchar(64) COLLATE "pg_catalog"."default",
    "master_id"        varchar(64) COLLATE "pg_catalog"."default",
    "deputy_master_id" varchar(64) COLLATE "pg_catalog"."default",
    "name"             varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "category"         varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "sort"             int4                                       NOT NULL,
    "is_virtual"       bool                                       NOT NULL,
    "status"           varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "extra"            json                                       NOT NULL,
    "created_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"       varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"       varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_dept"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_dept"."parent_id" IS '父部门ID';
COMMENT
ON COLUMN "public"."sys_dept"."master_id" IS '主管ID';
COMMENT
ON COLUMN "public"."sys_dept"."deputy_master_id" IS '副主管ID';
COMMENT
ON COLUMN "public"."sys_dept"."name" IS '部门名称';
COMMENT
ON COLUMN "public"."sys_dept"."category" IS '部门类别';
COMMENT
ON COLUMN "public"."sys_dept"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_dept"."is_virtual" IS '是否虚拟部门';
COMMENT
ON COLUMN "public"."sys_dept"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_dept"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_dept"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_dept"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_dept"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_dept"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict"
(
    "id"         varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "code"       varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "label"      varchar(255) COLLATE "pg_catalog"."default",
    "value"      varchar(255) COLLATE "pg_catalog"."default",
    "color"      varchar(32) COLLATE "pg_catalog"."default",
    "category"   varchar(64) COLLATE "pg_catalog"."default",
    "parent_id"  varchar(32) COLLATE "pg_catalog"."default",
    "status"     varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
    "sort"       int4                                       NOT NULL,
    "created_at" timestamptz(6) NOT NULL DEFAULT now(),
    "created_by" varchar(64) COLLATE "pg_catalog"."default",
    "updated_at" timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_dict"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_dict"."code" IS '编码';
COMMENT
ON COLUMN "public"."sys_dict"."label" IS '标签';
COMMENT
ON COLUMN "public"."sys_dict"."value" IS '值';
COMMENT
ON COLUMN "public"."sys_dict"."color" IS '颜色';
COMMENT
ON COLUMN "public"."sys_dict"."category" IS '系统/业务分类：
系统/业务分类
';
COMMENT
ON COLUMN "public"."sys_dict"."parent_id" IS '父级ID';
COMMENT
ON COLUMN "public"."sys_dict"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_dict"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_dict"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_dict"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_dict"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_dict"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_file";
CREATE TABLE "public"."sys_file"
(
    "id"               varchar(64) COLLATE "pg_catalog"."default"   NOT NULL,
    "object_name"      varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "original_name"    varchar(255) COLLATE "pg_catalog"."default"  NOT NULL,
    "storage_provider" varchar(32) COLLATE "pg_catalog"."default"   NOT NULL,
    "bucket"           varchar(255) COLLATE "pg_catalog"."default",
    "content_type"     varchar(128) COLLATE "pg_catalog"."default"  NOT NULL,
    "size"             int8                                         NOT NULL,
    "url"              varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
    "created_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"       varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"       timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"       varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_file"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_file"."object_name" IS '对象存储路径';
COMMENT
ON COLUMN "public"."sys_file"."original_name" IS '原始文件名';
COMMENT
ON COLUMN "public"."sys_file"."storage_provider" IS '存储服务商：local/minio/rustfs/oss/s3';
COMMENT
ON COLUMN "public"."sys_file"."bucket" IS '存储桶';
COMMENT
ON COLUMN "public"."sys_file"."content_type" IS '文件类型';
COMMENT
ON COLUMN "public"."sys_file"."size" IS '文件大小';
COMMENT
ON COLUMN "public"."sys_file"."url" IS '访问地址';
COMMENT
ON COLUMN "public"."sys_file"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_file"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_file"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_file"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_group";
CREATE TABLE "public"."sys_group"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "owner_dept_id" varchar(64) COLLATE "pg_catalog"."default",
    "description"   text COLLATE "pg_catalog"."default",
    "status"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "extra"         json                                       NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_group"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_group"."name" IS '账户组名称';
COMMENT
ON COLUMN "public"."sys_group"."owner_dept_id" IS '所属部门ID';
COMMENT
ON COLUMN "public"."sys_group"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_group"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_group"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_group"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_group"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_group"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_group"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_iam_relation
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_iam_relation";
CREATE TABLE "public"."sys_iam_relation"
(
    "id"                    varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "subject_type"          varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "subject_id"            varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "account_type"          varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "relation_type"         varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_type"           varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_id"             varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "target_key"            varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "grant_mode"            varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "data_scope"            varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "custom_scope_dept_ids" json                                        NOT NULL,
    "is_primary"            bool                                        NOT NULL,
    "sort"                  int4                                        NOT NULL,
    "status"                varchar(32) COLLATE "pg_catalog"."default"  NOT NULL,
    "description"           text COLLATE "pg_catalog"."default",
    "reason"                text COLLATE "pg_catalog"."default",
    "expired_at"            timestamptz(6),
    "extra"                 json                                        NOT NULL,
    "created_at"            timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"            varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"            timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"            varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_iam_relation"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_iam_relation"."subject_type" IS '主体类型';
COMMENT
ON COLUMN "public"."sys_iam_relation"."subject_id" IS '主体ID';
COMMENT
ON COLUMN "public"."sys_iam_relation"."account_type" IS '账户类型';
COMMENT
ON COLUMN "public"."sys_iam_relation"."relation_type" IS '关系类型';
COMMENT
ON COLUMN "public"."sys_iam_relation"."target_type" IS '目标类型';
COMMENT
ON COLUMN "public"."sys_iam_relation"."target_id" IS '目标ID';
COMMENT
ON COLUMN "public"."sys_iam_relation"."target_key" IS '目标标识';
COMMENT
ON COLUMN "public"."sys_iam_relation"."grant_mode" IS '授权模式';
COMMENT
ON COLUMN "public"."sys_iam_relation"."data_scope" IS '数据范围';
COMMENT
ON COLUMN "public"."sys_iam_relation"."custom_scope_dept_ids" IS '自定义数据范围部门ID列表';
COMMENT
ON COLUMN "public"."sys_iam_relation"."is_primary" IS '主关系';
COMMENT
ON COLUMN "public"."sys_iam_relation"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_iam_relation"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_iam_relation"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_iam_relation"."reason" IS '授权原因';
COMMENT
ON COLUMN "public"."sys_iam_relation"."expired_at" IS '失效时间';
COMMENT
ON COLUMN "public"."sys_iam_relation"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_iam_relation"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_iam_relation"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_iam_relation"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_iam_relation"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_operation_audit_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_operation_audit_log";
CREATE TABLE "public"."sys_operation_audit_log"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "module"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "resource_type" varchar(128) COLLATE "pg_catalog"."default",
    "resource_id"   varchar(128) COLLATE "pg_catalog"."default",
    "action"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "summary"       varchar(255) COLLATE "pg_catalog"."default",
    "before_data"   json,
    "after_data"    json,
    "account_id"    varchar(64) COLLATE "pg_catalog"."default",
    "account_type"  varchar(32) COLLATE "pg_catalog"."default",
    "request_id"    varchar(64) COLLATE "pg_catalog"."default",
    "ip"            varchar(64) COLLATE "pg_catalog"."default",
    "user_agent"    varchar(512) COLLATE "pg_catalog"."default",
    "success"       bool                                       NOT NULL,
    "error_message" text COLLATE "pg_catalog"."default",
    "created_at"    timestamptz(6) NOT NULL DEFAULT now()
)
;
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."module" IS '模块';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."resource_type" IS '资源类型';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."resource_id" IS '资源ID';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."action" IS '操作';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."summary" IS '摘要';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."before_data" IS '变更前数据';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."after_data" IS '变更后数据';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."account_id" IS '操作账号ID';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."account_type" IS '操作账号类型';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."request_id" IS '请求ID';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."ip" IS '客户端IP';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."user_agent" IS 'User-Agent';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."success" IS '是否成功';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."error_message" IS '错误信息';
COMMENT
ON COLUMN "public"."sys_operation_audit_log"."created_at" IS '创建时间';

-- ----------------------------
-- Table structure for sys_operation_audit_outbox
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_operation_audit_outbox";
CREATE TABLE "public"."sys_operation_audit_outbox"
(
    "id"         varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "payload"    text COLLATE "pg_catalog"."default"        NOT NULL,
    "status"     varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "attempts"   int4                                       NOT NULL,
    "created_at" timestamptz(6) NOT NULL DEFAULT now(),
    "claimed_at" timestamptz(6)
)
;
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."payload" IS '事件 JSON';
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."status" IS 'PENDING|CLAIMED|DONE';
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."attempts" IS '尝试次数';
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_operation_audit_outbox"."claimed_at" IS '认领时间';

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_position";
CREATE TABLE "public"."sys_position"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "category"      varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "owner_dept_id" varchar(64) COLLATE "pg_catalog"."default",
    "sort"          int4                                       NOT NULL,
    "is_virtual"    bool                                       NOT NULL,
    "status"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "description"   text COLLATE "pg_catalog"."default",
    "extra"         json                                       NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_position"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_position"."name" IS '职位名称';
COMMENT
ON COLUMN "public"."sys_position"."category" IS '职位类别';
COMMENT
ON COLUMN "public"."sys_position"."owner_dept_id" IS '所属部门ID';
COMMENT
ON COLUMN "public"."sys_position"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_position"."is_virtual" IS '是否虚拟职位';
COMMENT
ON COLUMN "public"."sys_position"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_position"."description" IS '职位描述';
COMMENT
ON COLUMN "public"."sys_position"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_position"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_position"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_position"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_position"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_resource";
CREATE TABLE "public"."sys_resource"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "parent_id"     varchar(64) COLLATE "pg_catalog"."default",
    "code"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "resource_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "module_id"     varchar(64) COLLATE "pg_catalog"."default",
    "path"          varchar(255) COLLATE "pg_catalog"."default",
    "component"     varchar(255) COLLATE "pg_catalog"."default",
    "redirect"      varchar(255) COLLATE "pg_catalog"."default",
    "icon"          varchar(255) COLLATE "pg_catalog"."default",
    "color"         varchar(32) COLLATE "pg_catalog"."default",
    "href"          varchar(255) COLLATE "pg_catalog"."default",
    "sort"          int4                                       NOT NULL,
    "is_visible"    bool                                       NOT NULL,
    "is_cache"      bool                                       NOT NULL,
    "is_affix"      bool                                       NOT NULL,
    "status"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "description"   text COLLATE "pg_catalog"."default",
    "layout"        varchar(255) COLLATE "pg_catalog"."default",
    "extra"         json                                       NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_resource"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_resource"."parent_id" IS '父资源ID';
COMMENT
ON COLUMN "public"."sys_resource"."code" IS '资源编码';
COMMENT
ON COLUMN "public"."sys_resource"."name" IS '资源名称';
COMMENT
ON COLUMN "public"."sys_resource"."resource_type" IS '资源类型';
COMMENT
ON COLUMN "public"."sys_resource"."module_id" IS '所属资源模块ID';
COMMENT
ON COLUMN "public"."sys_resource"."path" IS '路由路径';
COMMENT
ON COLUMN "public"."sys_resource"."component" IS '前端组件';
COMMENT
ON COLUMN "public"."sys_resource"."redirect" IS '重定向地址';
COMMENT
ON COLUMN "public"."sys_resource"."icon" IS '图标';
COMMENT
ON COLUMN "public"."sys_resource"."color" IS '颜色';
COMMENT
ON COLUMN "public"."sys_resource"."href" IS '外链地址';
COMMENT
ON COLUMN "public"."sys_resource"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_resource"."is_visible" IS '是否可见';
COMMENT
ON COLUMN "public"."sys_resource"."is_cache" IS '是否缓存';
COMMENT
ON COLUMN "public"."sys_resource"."is_affix" IS '是否固定标签';
COMMENT
ON COLUMN "public"."sys_resource"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_resource"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_resource"."layout" IS '布局类型';
COMMENT
ON COLUMN "public"."sys_resource"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_resource"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_resource"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_resource"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_resource"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_resource_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_resource_module";
CREATE TABLE "public"."sys_resource_module"
(
    "id"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "code"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "client"      varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "icon"        varchar(255) COLLATE "pg_catalog"."default",
    "color"       varchar(32) COLLATE "pg_catalog"."default",
    "sort"        int4                                       NOT NULL,
    "status"      varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "description" text COLLATE "pg_catalog"."default",
    "extra"       json                                       NOT NULL,
    "created_at"  timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"  varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"  timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"  varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_resource_module"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_resource_module"."name" IS '模块名称';
COMMENT
ON COLUMN "public"."sys_resource_module"."code" IS '模块编码';
COMMENT
ON COLUMN "public"."sys_resource_module"."client" IS '所属端';
COMMENT
ON COLUMN "public"."sys_resource_module"."icon" IS '图标';
COMMENT
ON COLUMN "public"."sys_resource_module"."color" IS '颜色';
COMMENT
ON COLUMN "public"."sys_resource_module"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_resource_module"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_resource_module"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_resource_module"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_resource_module"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_resource_module"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_resource_module"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_resource_module"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role"
(
    "id"            varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "code"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"          varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "category"      varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "scope_type"    varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "owner_dept_id" varchar(64) COLLATE "pg_catalog"."default",
    "sort"          int4                                       NOT NULL,
    "status"        varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
    "is_builtin"    bool                                       NOT NULL,
    "description"   text COLLATE "pg_catalog"."default",
    "extra"         json                                       NOT NULL,
    "created_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "created_by"    varchar(64) COLLATE "pg_catalog"."default",
    "updated_at"    timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by"    varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_role"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_role"."code" IS '角色编码';
COMMENT
ON COLUMN "public"."sys_role"."name" IS '角色名称';
COMMENT
ON COLUMN "public"."sys_role"."category" IS '角色分类';
COMMENT
ON COLUMN "public"."sys_role"."scope_type" IS '角色作用域类型';
COMMENT
ON COLUMN "public"."sys_role"."owner_dept_id" IS '所属部门ID';
COMMENT
ON COLUMN "public"."sys_role"."sort" IS '排序';
COMMENT
ON COLUMN "public"."sys_role"."status" IS '状态';
COMMENT
ON COLUMN "public"."sys_role"."is_builtin" IS '是否内置角色';
COMMENT
ON COLUMN "public"."sys_role"."description" IS '描述';
COMMENT
ON COLUMN "public"."sys_role"."extra" IS '扩展信息';
COMMENT
ON COLUMN "public"."sys_role"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_role"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_role"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_role"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for sys_weak_password
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_weak_password";
CREATE TABLE "public"."sys_weak_password"
(
    "id"         varchar(64) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "created_at" timestamptz(6) NOT NULL DEFAULT now(),
    "created_by" varchar(64) COLLATE "pg_catalog"."default",
    "updated_at" timestamptz(6) NOT NULL DEFAULT now(),
    "updated_by" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT
ON COLUMN "public"."sys_weak_password"."id" IS '主键';
COMMENT
ON COLUMN "public"."sys_weak_password"."password" IS '弱密码值';
COMMENT
ON COLUMN "public"."sys_weak_password"."created_at" IS '创建时间';
COMMENT
ON COLUMN "public"."sys_weak_password"."created_by" IS '创建人';
COMMENT
ON COLUMN "public"."sys_weak_password"."updated_at" IS '更新时间';
COMMENT
ON COLUMN "public"."sys_weak_password"."updated_by" IS '更新人';

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_group";
CREATE TABLE "public"."xxl_job_group"
(
    "id"           int4                                       NOT NULL DEFAULT nextval('xxl_job_group_id_seq'::regclass),
    "app_name"     varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "title"        varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
    "address_type" int2                                       NOT NULL DEFAULT 0,
    "address_list" text COLLATE "pg_catalog"."default",
    "update_time"  timestamp(6)
)
;

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_info";
CREATE TABLE "public"."xxl_job_info"
(
    "id"                        int4                                        NOT NULL DEFAULT nextval('xxl_job_info_id_seq'::regclass),
    "job_group"                 int4                                        NOT NULL,
    "job_desc"                  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "add_time"                  timestamp(6),
    "update_time"               timestamp(6),
    "author"                    varchar(64) COLLATE "pg_catalog"."default"           DEFAULT NULL::character varying,
    "alarm_email"               varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "schedule_type"             varchar(50) COLLATE "pg_catalog"."default"  NOT NULL DEFAULT 'NONE'::character varying,
    "schedule_conf"             varchar(128) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "misfire_strategy"          varchar(50) COLLATE "pg_catalog"."default"  NOT NULL DEFAULT 'DO_NOTHING'::character varying,
    "executor_route_strategy"   varchar(50) COLLATE "pg_catalog"."default"           DEFAULT NULL::character varying,
    "executor_handler"          varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "executor_param"            text COLLATE "pg_catalog"."default",
    "executor_block_strategy"   varchar(50) COLLATE "pg_catalog"."default"           DEFAULT NULL::character varying,
    "executor_timeout"          int4                                        NOT NULL DEFAULT 0,
    "executor_fail_retry_count" int4                                        NOT NULL DEFAULT 0,
    "glue_type"                 varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "glue_source"               text COLLATE "pg_catalog"."default",
    "glue_remark"               varchar(128) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "glue_updatetime"           timestamp(6),
    "child_jobid"               varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "trigger_status"            int2                                        NOT NULL DEFAULT 0,
    "trigger_last_time"         int8                                        NOT NULL DEFAULT 0,
    "trigger_next_time"         int8                                        NOT NULL DEFAULT 0
)
;

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_lock";
CREATE TABLE "public"."xxl_job_lock"
(
    "lock_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_log";
CREATE TABLE "public"."xxl_job_log"
(
    "id"                        int8 NOT NULL                               DEFAULT nextval('xxl_job_log_id_seq'::regclass),
    "job_group"                 int4 NOT NULL,
    "job_id"                    int4 NOT NULL,
    "executor_address"          varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "executor_handler"          varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "executor_param"            text COLLATE "pg_catalog"."default",
    "executor_sharding_param"   varchar(20) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "executor_fail_retry_count" int4 NOT NULL                               DEFAULT 0,
    "trigger_time"              timestamp(6),
    "trigger_code"              int4 NOT NULL,
    "trigger_msg"               text COLLATE "pg_catalog"."default",
    "handle_time"               timestamp(6),
    "handle_code"               int4 NOT NULL,
    "handle_msg"                text COLLATE "pg_catalog"."default",
    "alarm_status"              int2 NOT NULL                               DEFAULT 0
)
;

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_log_report";
CREATE TABLE "public"."xxl_job_log_report"
(
    "id"            int4 NOT NULL DEFAULT nextval('xxl_job_log_report_id_seq'::regclass),
    "trigger_day"   timestamp(6),
    "running_count" int4 NOT NULL DEFAULT 0,
    "suc_count"     int4 NOT NULL DEFAULT 0,
    "fail_count"    int4 NOT NULL DEFAULT 0,
    "update_time"   timestamp(6)
)
;

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_logglue";
CREATE TABLE "public"."xxl_job_logglue"
(
    "id"          int4                                        NOT NULL DEFAULT nextval('xxl_job_logglue_id_seq'::regclass),
    "job_id"      int4                                        NOT NULL,
    "glue_type"   varchar(50) COLLATE "pg_catalog"."default"           DEFAULT NULL::character varying,
    "glue_source" text COLLATE "pg_catalog"."default",
    "glue_remark" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
    "add_time"    timestamp(6),
    "update_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_registry";
CREATE TABLE "public"."xxl_job_registry"
(
    "id"             int8                                        NOT NULL DEFAULT nextval('xxl_job_registry_id_seq'::regclass),
    "registry_group" varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "registry_key"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "registry_value" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "update_time"    timestamp(6)
)
;

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."xxl_job_user";
CREATE TABLE "public"."xxl_job_user"
(
    "id"         int4                                        NOT NULL DEFAULT nextval('xxl_job_user_id_seq'::regclass),
    "username"   varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "token"      varchar(100) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "role"       int2                                        NOT NULL,
    "permission" varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying
)
;

-- ----------------------------
-- Primary Key structure for table admin_user_profile
-- ----------------------------
ALTER TABLE "public"."admin_user_profile"
    ADD CONSTRAINT "pk_admin_user_profile" PRIMARY KEY ("account_id");

-- ----------------------------
-- Primary Key structure for table alembic_version
-- ----------------------------
ALTER TABLE "public"."alembic_version"
    ADD CONSTRAINT "alembic_version_pkc" PRIMARY KEY ("version_num");

-- ----------------------------
-- Indexes structure for table cg_test_activity
-- ----------------------------
CREATE INDEX "ix_cg_test_activity_owner_dept_id" ON "public"."cg_test_activity" USING btree (
    "owner_dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table cg_test_activity
-- ----------------------------
ALTER TABLE "public"."cg_test_activity"
    ADD CONSTRAINT "pk_cg_test_activity" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table cg_test_catalog
-- ----------------------------
CREATE INDEX "ix_cg_test_catalog_owner_dept_id" ON "public"."cg_test_catalog" USING btree (
    "owner_dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table cg_test_catalog
-- ----------------------------
ALTER TABLE "public"."cg_test_catalog"
    ADD CONSTRAINT "pk_cg_test_catalog" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table cg_test_knowledge_category
-- ----------------------------
CREATE INDEX "ix_cg_test_knowledge_category_owner_dept_id" ON "public"."cg_test_knowledge_category" USING btree (
    "owner_dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table cg_test_knowledge_category
-- ----------------------------
ALTER TABLE "public"."cg_test_knowledge_category"
    ADD CONSTRAINT "pk_cg_test_knowledge_category" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cg_test_knowledge_doc
-- ----------------------------
ALTER TABLE "public"."cg_test_knowledge_doc"
    ADD CONSTRAINT "pk_cg_test_knowledge_doc" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table cg_test_order
-- ----------------------------
CREATE INDEX "ix_cg_test_order_owner_dept_id" ON "public"."cg_test_order" USING btree (
    "owner_dept_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table cg_test_order
-- ----------------------------
ALTER TABLE "public"."cg_test_order"
    ADD CONSTRAINT "pk_cg_test_order" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cg_test_order_item
-- ----------------------------
ALTER TABLE "public"."cg_test_order_item"
    ADD CONSTRAINT "pk_cg_test_order_item" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table msg_feedback
-- ----------------------------
ALTER TABLE "public"."msg_feedback"
    ADD CONSTRAINT "pk_msg_feedback" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table msg_notice
-- ----------------------------
ALTER TABLE "public"."msg_notice"
    ADD CONSTRAINT "pk_msg_notice" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table msg_notice_read
-- ----------------------------
ALTER TABLE "public"."msg_notice_read"
    ADD CONSTRAINT "uq_msg_notice_read_account" UNIQUE ("notice_id", "account_type", "account_id");

-- ----------------------------
-- Primary Key structure for table msg_notice_read
-- ----------------------------
ALTER TABLE "public"."msg_notice_read"
    ADD CONSTRAINT "pk_msg_notice_read" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table portal_user_profile
-- ----------------------------
ALTER TABLE "public"."portal_user_profile"
    ADD CONSTRAINT "pk_portal_user_profile" PRIMARY KEY ("account_id");

-- ----------------------------
-- Primary Key structure for table sys_account
-- ----------------------------
ALTER TABLE "public"."sys_account"
    ADD CONSTRAINT "pk_sys_account" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_account_identity
-- ----------------------------
ALTER TABLE "public"."sys_account_identity"
    ADD CONSTRAINT "uq_sys_account_identity_type_identifier" UNIQUE ("identity_type", "identifier");

-- ----------------------------
-- Primary Key structure for table sys_account_identity
-- ----------------------------
ALTER TABLE "public"."sys_account_identity"
    ADD CONSTRAINT "pk_sys_account_identity" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_account_password_history
-- ----------------------------
CREATE INDEX "idx_pwd_history_account_created" ON "public"."sys_account_password_history" USING btree (
    "account_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "created_at" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_account_password_history
-- ----------------------------
ALTER TABLE "public"."sys_account_password_history"
    ADD CONSTRAINT "pk_sys_account_password_history" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_alert_log
-- ----------------------------
ALTER TABLE "public"."sys_alert_log"
    ADD CONSTRAINT "pk_sys_alert_log" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_banner
-- ----------------------------
CREATE INDEX "ix_sys_banner_position_status_sort" ON "public"."sys_banner" USING btree (
    "position" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sort" "pg_catalog"."int4_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_banner
-- ----------------------------
ALTER TABLE "public"."sys_banner"
    ADD CONSTRAINT "pk_sys_banner" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_client_module
-- ----------------------------
ALTER TABLE "public"."sys_client_module"
    ADD CONSTRAINT "uq_sys_client_module_code" UNIQUE ("code");

-- ----------------------------
-- Primary Key structure for table sys_client_module
-- ----------------------------
ALTER TABLE "public"."sys_client_module"
    ADD CONSTRAINT "pk_sys_client_module" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_client_resource
-- ----------------------------
ALTER TABLE "public"."sys_client_resource"
    ADD CONSTRAINT "uq_sys_client_resource_module_id_code" UNIQUE ("module_id", "code");

-- ----------------------------
-- Primary Key structure for table sys_client_resource
-- ----------------------------
ALTER TABLE "public"."sys_client_resource"
    ADD CONSTRAINT "pk_sys_client_resource" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_codegen_field
-- ----------------------------
CREATE INDEX "ix_sys_codegen_field_plan_role_sort" ON "public"."sys_codegen_field" USING btree (
    "plan_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "table_role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "sort" "pg_catalog"."int4_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Uniques structure for table sys_codegen_field
-- ----------------------------
ALTER TABLE "public"."sys_codegen_field"
    ADD CONSTRAINT "uq_sys_codegen_field_plan_role_column" UNIQUE ("plan_id", "table_role", "column_name");

-- ----------------------------
-- Primary Key structure for table sys_codegen_field
-- ----------------------------
ALTER TABLE "public"."sys_codegen_field"
    ADD CONSTRAINT "pk_sys_codegen_field" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_codegen_plan
-- ----------------------------
CREATE INDEX "ix_sys_codegen_plan_gen_type" ON "public"."sys_codegen_plan" USING btree (
    "gen_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "ix_sys_codegen_plan_main_table" ON "public"."sys_codegen_plan" USING btree (
    "main_table" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Uniques structure for table sys_codegen_plan
-- ----------------------------
ALTER TABLE "public"."sys_codegen_plan"
    ADD CONSTRAINT "uq_sys_codegen_plan_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table sys_codegen_plan
-- ----------------------------
ALTER TABLE "public"."sys_codegen_plan"
    ADD CONSTRAINT "pk_sys_codegen_plan" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_config
-- ----------------------------
CREATE INDEX "idx_sys_config_category" ON "public"."sys_config" USING btree (
    "category" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_sys_config_category_scope_scene" ON "public"."sys_config" USING btree (
    "category" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "scope" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "scene" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_sys_config_key" ON "public"."sys_config" USING btree (
    "config_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config"
    ADD CONSTRAINT "pk_sys_config" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept"
    ADD CONSTRAINT "pk_sys_dept" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict
-- ----------------------------
CREATE INDEX "idx_sys_dict_category" ON "public"."sys_dict" USING btree (
    "category" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE UNIQUE INDEX "idx_sys_dict_code" ON "public"."sys_dict" USING btree (
    "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_sys_dict_parent_id" ON "public"."sys_dict" USING btree (
    "parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict"
    ADD CONSTRAINT "pk_sys_dict" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_file
-- ----------------------------
ALTER TABLE "public"."sys_file"
    ADD CONSTRAINT "uq_sys_file_object_name" UNIQUE ("object_name");

-- ----------------------------
-- Primary Key structure for table sys_file
-- ----------------------------
ALTER TABLE "public"."sys_file"
    ADD CONSTRAINT "pk_sys_file" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_group
-- ----------------------------
ALTER TABLE "public"."sys_group"
    ADD CONSTRAINT "uq_sys_group_name" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table sys_group
-- ----------------------------
ALTER TABLE "public"."sys_group"
    ADD CONSTRAINT "pk_sys_group" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_iam_relation
-- ----------------------------
CREATE INDEX "ix_sys_iam_relation_account_type_relation" ON "public"."sys_iam_relation" USING btree (
    "account_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "relation_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "ix_sys_iam_relation_subject" ON "public"."sys_iam_relation" USING btree (
    "subject_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "subject_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "relation_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "ix_sys_iam_relation_target" ON "public"."sys_iam_relation" USING btree (
    "target_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "target_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "target_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Uniques structure for table sys_iam_relation
-- ----------------------------
ALTER TABLE "public"."sys_iam_relation"
    ADD CONSTRAINT "uq_sys_iam_relation_subject_relation_target" UNIQUE ("subject_type", "subject_id", "relation_type",
                                                                         "target_type", "target_id", "target_key",
                                                                         "account_type");

-- ----------------------------
-- Primary Key structure for table sys_iam_relation
-- ----------------------------
ALTER TABLE "public"."sys_iam_relation"
    ADD CONSTRAINT "pk_sys_iam_relation" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_operation_audit_log
-- ----------------------------
CREATE INDEX "idx_sys_operation_audit_account_id" ON "public"."sys_operation_audit_log" USING btree (
    "account_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_sys_operation_audit_created_at" ON "public"."sys_operation_audit_log" USING btree (
    "created_at" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_sys_operation_audit_module_action" ON "public"."sys_operation_audit_log" USING btree (
    "module" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "action" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );
CREATE INDEX "idx_sys_operation_audit_resource" ON "public"."sys_operation_audit_log" USING btree (
    "resource_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
    "resource_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_operation_audit_log
-- ----------------------------
ALTER TABLE "public"."sys_operation_audit_log"
    ADD CONSTRAINT "pk_sys_operation_audit_log" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_operation_audit_outbox
-- ----------------------------
ALTER TABLE "public"."sys_operation_audit_outbox"
    ADD CONSTRAINT "pk_sys_operation_audit_outbox" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_position
-- ----------------------------
ALTER TABLE "public"."sys_position"
    ADD CONSTRAINT "pk_sys_position" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_resource
-- ----------------------------
ALTER TABLE "public"."sys_resource"
    ADD CONSTRAINT "uq_sys_resource_module_id_code" UNIQUE ("module_id", "code");

-- ----------------------------
-- Primary Key structure for table sys_resource
-- ----------------------------
ALTER TABLE "public"."sys_resource"
    ADD CONSTRAINT "pk_sys_resource" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_resource_module
-- ----------------------------
ALTER TABLE "public"."sys_resource_module"
    ADD CONSTRAINT "uq_sys_resource_module_code" UNIQUE ("code");

-- ----------------------------
-- Primary Key structure for table sys_resource_module
-- ----------------------------
ALTER TABLE "public"."sys_resource_module"
    ADD CONSTRAINT "pk_sys_resource_module" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role"
    ADD CONSTRAINT "uq_sys_role_code" UNIQUE ("code");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role"
    ADD CONSTRAINT "pk_sys_role" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_weak_password
-- ----------------------------
CREATE UNIQUE INDEX "idx_sys_weak_password_password" ON "public"."sys_weak_password" USING btree (
    "password" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_weak_password
-- ----------------------------
ALTER TABLE "public"."sys_weak_password"
    ADD CONSTRAINT "pk_sys_weak_password" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table xxl_job_group
-- ----------------------------
ALTER TABLE "public"."xxl_job_group"
    ADD CONSTRAINT "xxl_job_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table xxl_job_info
-- ----------------------------
ALTER TABLE "public"."xxl_job_info"
    ADD CONSTRAINT "xxl_job_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table xxl_job_lock
-- ----------------------------
ALTER TABLE "public"."xxl_job_lock"
    ADD CONSTRAINT "xxl_job_lock_pkey" PRIMARY KEY ("lock_name");

-- ----------------------------
-- Indexes structure for table xxl_job_log
-- ----------------------------
CREATE INDEX "i_handle_code" ON "public"."xxl_job_log" USING btree (
    "handle_code" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "i_jobgroup" ON "public"."xxl_job_log" USING btree (
    "job_group" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "i_jobid" ON "public"."xxl_job_log" USING btree (
    "job_id" "pg_catalog"."int4_ops" ASC NULLS LAST
    );
CREATE INDEX "i_trigger_time" ON "public"."xxl_job_log" USING btree (
    "trigger_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table xxl_job_log
-- ----------------------------
ALTER TABLE "public"."xxl_job_log"
    ADD CONSTRAINT "xxl_job_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table xxl_job_log_report
-- ----------------------------
ALTER TABLE "public"."xxl_job_log_report"
    ADD CONSTRAINT "i_trigger_day" UNIQUE ("trigger_day");

-- ----------------------------
-- Primary Key structure for table xxl_job_log_report
-- ----------------------------
ALTER TABLE "public"."xxl_job_log_report"
    ADD CONSTRAINT "xxl_job_log_report_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table xxl_job_logglue
-- ----------------------------
ALTER TABLE "public"."xxl_job_logglue"
    ADD CONSTRAINT "xxl_job_logglue_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table xxl_job_registry
-- ----------------------------
ALTER TABLE "public"."xxl_job_registry"
    ADD CONSTRAINT "i_g_k_v" UNIQUE ("registry_group", "registry_key", "registry_value");

-- ----------------------------
-- Primary Key structure for table xxl_job_registry
-- ----------------------------
ALTER TABLE "public"."xxl_job_registry"
    ADD CONSTRAINT "xxl_job_registry_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table xxl_job_user
-- ----------------------------
ALTER TABLE "public"."xxl_job_user"
    ADD CONSTRAINT "i_username" UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table xxl_job_user
-- ----------------------------
ALTER TABLE "public"."xxl_job_user"
    ADD CONSTRAINT "xxl_job_user_pkey" PRIMARY KEY ("id");
