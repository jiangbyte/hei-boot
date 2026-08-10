-- Aligned with hei-fastapi Alembic revision 27c193fc4b22 (squash baseline).
-- No physical FKs. Replaces prior Flyway V1–V9 history.

CREATE TABLE admin_user_profile
(
    account_id VARCHAR(64) NOT NULL,
    name       VARCHAR(64),
    nickname   VARCHAR(64),
    avatar     TEXT,
    signature  TEXT,
    phone      VARCHAR(32),
    email      VARCHAR(128),
    remark     TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by VARCHAR(64),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by VARCHAR(64),
    PRIMARY KEY (account_id)
);

CREATE TABLE cg_test_activity
(
    id               VARCHAR(64)  NOT NULL,
    code             VARCHAR(64)  NOT NULL,
    name             VARCHAR(120) NOT NULL,
    category         VARCHAR(32),
    type             VARCHAR(32)  NOT NULL,
    status           VARCHAR(32)  NOT NULL,
    cover_url        VARCHAR(512),
    description      TEXT,
    start_at         TIMESTAMPTZ  NOT NULL,
    end_at           TIMESTAMPTZ,
    max_participants INTEGER      NOT NULL DEFAULT 0,
    price            NUMERIC      NOT NULL DEFAULT 0,
    is_public        BOOLEAN      NOT NULL DEFAULT FALSE,
    need_approval    BOOLEAN      NOT NULL DEFAULT FALSE,
    rule_config      JSON         NOT NULL DEFAULT '{}'::json,
    extra            JSON,
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by       VARCHAR(64),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by       VARCHAR(64),
    owner_dept_id    VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE cg_test_catalog
(
    id            VARCHAR(64)  NOT NULL,
    parent_id     VARCHAR(64),
    code          VARCHAR(64)  NOT NULL,
    name          VARCHAR(120) NOT NULL,
    category      VARCHAR(32),
    status        VARCHAR(32)  NOT NULL,
    sort          INTEGER      NOT NULL DEFAULT 0,
    is_visible    BOOLEAN      NOT NULL DEFAULT FALSE,
    icon          VARCHAR(128),
    description   TEXT,
    extra         JSON         NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    owner_dept_id VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE cg_test_knowledge_category
(
    id            VARCHAR(64)  NOT NULL,
    parent_id     VARCHAR(64),
    code          VARCHAR(64)  NOT NULL,
    name          VARCHAR(120) NOT NULL,
    status        VARCHAR(32)  NOT NULL,
    sort          INTEGER      NOT NULL DEFAULT 0,
    is_visible    BOOLEAN      NOT NULL DEFAULT FALSE,
    description   TEXT,
    extra         JSON         NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    owner_dept_id VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE cg_test_knowledge_doc
(
    id           VARCHAR(64)  NOT NULL,
    category_id  VARCHAR(64)  NOT NULL,
    code         VARCHAR(64)  NOT NULL,
    title        VARCHAR(160) NOT NULL,
    type         VARCHAR(32)  NOT NULL,
    status       VARCHAR(32)  NOT NULL,
    summary      VARCHAR(512),
    content      TEXT,
    author       VARCHAR(64),
    published_at TIMESTAMPTZ,
    view_count   INTEGER      NOT NULL DEFAULT 0,
    sort         INTEGER      NOT NULL DEFAULT 0,
    is_top       BOOLEAN      NOT NULL DEFAULT FALSE,
    settings     JSON         NOT NULL DEFAULT '{}'::json,
    extra        JSON,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by   VARCHAR(64),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by   VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE cg_test_order
(
    id             VARCHAR(64)  NOT NULL,
    order_no       VARCHAR(64)  NOT NULL,
    name           VARCHAR(120) NOT NULL,
    customer_name  VARCHAR(120) NOT NULL,
    customer_phone VARCHAR(32),
    status         VARCHAR(32)  NOT NULL,
    type           VARCHAR(32)  NOT NULL,
    ordered_at     TIMESTAMPTZ  NOT NULL,
    paid_at        TIMESTAMPTZ,
    total_amount   NUMERIC      NOT NULL DEFAULT 0,
    item_count     INTEGER      NOT NULL DEFAULT 0,
    need_invoice   BOOLEAN      NOT NULL DEFAULT FALSE,
    invoice_config JSON         NOT NULL DEFAULT '{}'::json,
    remark         TEXT,
    extra          JSON,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by     VARCHAR(64),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by     VARCHAR(64),
    owner_dept_id  VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE cg_test_order_item
(
    id          VARCHAR(64)  NOT NULL,
    order_id    VARCHAR(64)  NOT NULL,
    sku_code    VARCHAR(64)  NOT NULL,
    name        VARCHAR(120) NOT NULL,
    category    VARCHAR(32),
    status      VARCHAR(32)  NOT NULL,
    quantity    INTEGER      NOT NULL DEFAULT 0,
    unit_price  NUMERIC      NOT NULL DEFAULT 0,
    shipped_at  TIMESTAMPTZ,
    is_gift     BOOLEAN      NOT NULL DEFAULT FALSE,
    item_config JSON         NOT NULL DEFAULT '{}'::json,
    remark      TEXT,
    extra       JSON,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by  VARCHAR(64),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by  VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE msg_feedback
(
    id                     VARCHAR(64)  NOT NULL,
    title                  VARCHAR(255) NOT NULL,
    content                TEXT         NOT NULL,
    category               VARCHAR(64)  NOT NULL,
    contact                VARCHAR(255),
    attach_object_names    JSON         NOT NULL DEFAULT '[]'::json,
    status                 VARCHAR(32)  NOT NULL,
    reply                  TEXT,
    replied_by             VARCHAR(64),
    replied_at             TIMESTAMPTZ,
    submitter_account_type VARCHAR(32)  NOT NULL,
    submitter_account_id   VARCHAR(64)  NOT NULL,
    created_at             TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by             VARCHAR(64),
    updated_at             TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by             VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE msg_notice
(
    id                   VARCHAR(64)  NOT NULL,
    kind                 VARCHAR(32)  NOT NULL,
    title                VARCHAR(255) NOT NULL,
    content              TEXT         NOT NULL,
    content_type         VARCHAR(32)  NOT NULL,
    category             VARCHAR(32),
    severity             VARCHAR(32)  NOT NULL,
    target_scope         VARCHAR(32)  NOT NULL,
    target_account_types JSON         NOT NULL DEFAULT '[]'::json,
    target_account_ids   JSON         NOT NULL DEFAULT '[]'::json,
    target_dept_ids      JSON         NOT NULL DEFAULT '[]'::json,
    target_role_ids      JSON         NOT NULL DEFAULT '[]'::json,
    publish_locations    JSON         NOT NULL DEFAULT '{}'::json,
    is_pinned            BOOLEAN      NOT NULL DEFAULT FALSE,
    pinned_until         TIMESTAMPTZ,
    sender_account_type  VARCHAR(32),
    sender_account_id    VARCHAR(64),
    source_type          VARCHAR(64),
    source_id            VARCHAR(64),
    status               VARCHAR(32)  NOT NULL,
    publish_at           TIMESTAMPTZ,
    revoked_at           TIMESTAMPTZ,
    expire_at            TIMESTAMPTZ,
    view_count           INTEGER      NOT NULL DEFAULT 0,
    extra                JSON         NOT NULL DEFAULT '{}'::json,
    created_at           TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by           VARCHAR(64),
    updated_at           TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by           VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE msg_notice_read
(
    id           VARCHAR(64) NOT NULL,
    notice_id    VARCHAR(64) NOT NULL,
    account_type VARCHAR(32) NOT NULL,
    account_id   VARCHAR(64) NOT NULL,
    read_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    CONSTRAINT uq_msg_notice_read_account UNIQUE (notice_id, account_type, account_id)
);

CREATE TABLE portal_user_profile
(
    account_id VARCHAR(64) NOT NULL,
    name       VARCHAR(64),
    nickname   VARCHAR(64),
    avatar     TEXT,
    signature  TEXT,
    phone      VARCHAR(32),
    email      VARCHAR(128),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by VARCHAR(64),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by VARCHAR(64),
    PRIMARY KEY (account_id)
);

CREATE TABLE sys_account (
                             id                  VARCHAR(64)  NOT NULL,
                             password_hash       VARCHAR(255) NOT NULL,
                             account_type        VARCHAR(32)  NOT NULL,
                             account_status      VARCHAR(32)  NOT NULL,
                             cancelled_at        TIMESTAMPTZ,
                             cancelled_by        VARCHAR(64),
                             cancel_reason       TEXT,
                             cancel_notify_email VARCHAR(128),
                             cancel_notify_phone VARCHAR(32),
                             last_login_ip       VARCHAR(64),
                             last_login_address  VARCHAR(255),
                             last_login_time     TIMESTAMPTZ,
                             last_login_device   TEXT,
                             latest_login_ip     VARCHAR(64),
    latest_login_address VARCHAR(255),
                             latest_login_time   TIMESTAMPTZ,
    latest_login_device TEXT,
                             created_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
                             created_by          VARCHAR(64),
                             updated_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
                             updated_by          VARCHAR(64),
                             PRIMARY KEY (id)
);

CREATE TABLE sys_account_identity (
                                      id            VARCHAR(64)  NOT NULL,
                                      account_id    VARCHAR(64)  NOT NULL,
                                      identity_type VARCHAR(32)  NOT NULL,
                                      identifier    VARCHAR(128) NOT NULL,
                                      verified      BOOLEAN      NOT NULL DEFAULT FALSE,
                                      is_primary    BOOLEAN      NOT NULL DEFAULT FALSE,
                                      bind_status   VARCHAR(32)  NOT NULL,
                                      created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
                                      created_by    VARCHAR(64),
                                      updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
                                      updated_by    VARCHAR(64),
                                      PRIMARY KEY (id),
    CONSTRAINT uq_sys_account_identity_type_identifier UNIQUE (identity_type, identifier)
);

CREATE TABLE sys_account_password_history (
                                              id            VARCHAR(64)  NOT NULL,
                                              account_id    VARCHAR(64)  NOT NULL,
                                              password_hash VARCHAR(255) NOT NULL,
                                              changed_by    VARCHAR(64),
                                              change_reason VARCHAR(64),
                                              created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
                                              PRIMARY KEY (id)
);

CREATE TABLE sys_alert_log
(
    id           VARCHAR(64)  NOT NULL,
    rule_name    VARCHAR(64)  NOT NULL,
    severity     VARCHAR(16)  NOT NULL,
    summary      VARCHAR(255) NOT NULL,
    details      JSON,
    notified_via VARCHAR(64),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE TABLE sys_banner
(
    id                   VARCHAR(64)  NOT NULL,
    title                VARCHAR(255) NOT NULL,
    image                VARCHAR(500) NOT NULL,
    url                  VARCHAR(500),
    link_type            VARCHAR(16)  NOT NULL,
    summary              VARCHAR(500),
    description          TEXT,
    category             VARCHAR(32)  NOT NULL,
    type                 VARCHAR(32)  NOT NULL,
    position             VARCHAR(32)  NOT NULL,
    target_account_types JSON         NOT NULL DEFAULT '[]'::json,
    sort                 INTEGER      NOT NULL DEFAULT 0,
    interaction_count    BIGINT       NOT NULL DEFAULT 0,
    status               VARCHAR(32)  NOT NULL,
    start_at             TIMESTAMPTZ,
    end_at               TIMESTAMPTZ,
    created_at           TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by           VARCHAR(64),
    updated_at           TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by           VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE sys_client_module
(
    id           VARCHAR(64) NOT NULL,
    name         VARCHAR(64) NOT NULL,
    code         VARCHAR(64) NOT NULL,
    account_type VARCHAR(32) NOT NULL,
    icon         VARCHAR(255),
    color        VARCHAR(32),
    sort         INTEGER     NOT NULL DEFAULT 0,
    status       VARCHAR(32) NOT NULL,
    description  TEXT,
    extra        JSON        NOT NULL DEFAULT '{}'::json,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   VARCHAR(64),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by   VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_client_module_code UNIQUE (code)
);

CREATE TABLE sys_client_resource
(
    id            VARCHAR(64) NOT NULL,
    parent_id     VARCHAR(64),
    code          VARCHAR(64) NOT NULL,
    name          VARCHAR(64) NOT NULL,
    resource_type VARCHAR(32) NOT NULL,
    module_id     VARCHAR(64),
    path          VARCHAR(255),
    component     VARCHAR(255),
    redirect      VARCHAR(255),
    icon          VARCHAR(255),
    color         VARCHAR(32),
    href          VARCHAR(255),
    sort          INTEGER     NOT NULL DEFAULT 0,
    is_visible    BOOLEAN     NOT NULL DEFAULT FALSE,
    is_cache      BOOLEAN     NOT NULL DEFAULT FALSE,
    is_affix      BOOLEAN     NOT NULL DEFAULT FALSE,
    status        VARCHAR(32) NOT NULL,
    description   TEXT,
    layout        VARCHAR(255),
    extra         JSON        NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_client_resource_module_id_code UNIQUE (module_id, code)
);

CREATE TABLE sys_codegen_field
(
    id              VARCHAR(64)  NOT NULL,
    plan_id         VARCHAR(64)  NOT NULL,
    table_role      VARCHAR(16)  NOT NULL,
    column_name     VARCHAR(128) NOT NULL,
    column_comment  VARCHAR(255),
    db_type         VARCHAR(128) NOT NULL,
    python_type     VARCHAR(64)  NOT NULL,
    typescript_type VARCHAR(64)  NOT NULL,
    form_widget     VARCHAR(32)  NOT NULL,
    dict_code       VARCHAR(128),
    query_operator  VARCHAR(32),
    show_in_table   BOOLEAN      NOT NULL DEFAULT FALSE,
    show_in_form    BOOLEAN      NOT NULL DEFAULT FALSE,
    show_in_detail  BOOLEAN      NOT NULL DEFAULT FALSE,
    show_in_query   BOOLEAN      NOT NULL DEFAULT FALSE,
    is_primary_key  BOOLEAN      NOT NULL DEFAULT FALSE,
    is_required     BOOLEAN      NOT NULL DEFAULT FALSE,
    is_unique       BOOLEAN      NOT NULL DEFAULT FALSE,
    is_nullable     BOOLEAN      NOT NULL DEFAULT FALSE,
    max_length      INTEGER,
    sort            INTEGER      NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by      VARCHAR(64),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by      VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_codegen_field_plan_role_column UNIQUE (plan_id, table_role, column_name)
);

CREATE TABLE sys_codegen_plan
(
    id                 VARCHAR(64)  NOT NULL,
    name               VARCHAR(128) NOT NULL,
    gen_type           VARCHAR(32)  NOT NULL,
    author             VARCHAR(64)  NOT NULL,
    description        TEXT,
    main_table         VARCHAR(128) NOT NULL,
    main_pk            VARCHAR(128) NOT NULL,
    main_entity_name   VARCHAR(128) NOT NULL,
    main_module_path   VARCHAR(255) NOT NULL,
    main_business_name VARCHAR(128) NOT NULL,
    api_prefix         VARCHAR(255) NOT NULL,
    permission_prefix  VARCHAR(128) NOT NULL,
    resource_module_id VARCHAR(64),
    parent_resource_id VARCHAR(64),
    menu_name          VARCHAR(64)  NOT NULL,
    menu_path          VARCHAR(255) NOT NULL,
    component_path     VARCHAR(255) NOT NULL,
    icon               VARCHAR(255),
    sort               INTEGER      NOT NULL DEFAULT 0,
    tree_parent_field  VARCHAR(128),
    tree_label_field   VARCHAR(128),
    sub_table          VARCHAR(128),
    sub_pk             VARCHAR(128),
    sub_foreign_key    VARCHAR(128),
    sub_entity_name    VARCHAR(128),
    sub_business_name  VARCHAR(128),
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by         VARCHAR(64),
    updated_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by         VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_codegen_plan_name UNIQUE (name)
);

CREATE TABLE sys_config (
                            id         VARCHAR(64)  NOT NULL,
                            config_key VARCHAR(255) NOT NULL,
    config_value TEXT,
                            category   VARCHAR(255),
                            remark     VARCHAR(255),
                            sort_code  INTEGER      NOT NULL DEFAULT 0,
                            value_type VARCHAR(32)  NOT NULL,
                            label      VARCHAR(128),
                            scope      VARCHAR(32),
                            scene      VARCHAR(64),
                            is_builtin BOOLEAN      NOT NULL DEFAULT FALSE,
                            ext_json   JSON         NOT NULL DEFAULT '{}'::json,
                            created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
                            created_by VARCHAR(64),
                            updated_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
                            updated_by VARCHAR(64),
                            PRIMARY KEY (id)
);

CREATE TABLE sys_dept
(
    id               VARCHAR(64) NOT NULL,
    parent_id        VARCHAR(64),
    master_id        VARCHAR(64),
    deputy_master_id VARCHAR(64),
    name             VARCHAR(64) NOT NULL,
    category         VARCHAR(64) NOT NULL,
    sort             INTEGER     NOT NULL DEFAULT 0,
    is_virtual       BOOLEAN     NOT NULL DEFAULT FALSE,
    status           VARCHAR(32) NOT NULL,
    extra            JSON        NOT NULL DEFAULT '{}'::json,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by       VARCHAR(64),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by       VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE sys_dict (
                          id         VARCHAR(32) NOT NULL,
                          code       VARCHAR(50) NOT NULL,
                          label      VARCHAR(255),
                          value      VARCHAR(255),
                          color      VARCHAR(32),
                          category   VARCHAR(64),
                          parent_id  VARCHAR(32),
                          status     VARCHAR(16) NOT NULL,
                          sort       INTEGER     NOT NULL DEFAULT 0,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                          created_by VARCHAR(64),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                          updated_by VARCHAR(64),
                          PRIMARY KEY (id)
);

CREATE TABLE sys_file (
                          id               VARCHAR(64)   NOT NULL,
                          object_name      VARCHAR(255)  NOT NULL,
                          original_name    VARCHAR(255)  NOT NULL,
                          storage_provider VARCHAR(32)   NOT NULL,
                          bucket           VARCHAR(255),
                          content_type     VARCHAR(128)  NOT NULL,
                          size             BIGINT        NOT NULL,
                          url              VARCHAR(1024) NOT NULL,
                          created_at       TIMESTAMPTZ   NOT NULL DEFAULT now(),
                          created_by       VARCHAR(64),
                          updated_at       TIMESTAMPTZ   NOT NULL DEFAULT now(),
                          updated_by       VARCHAR(64),
                          PRIMARY KEY (id),
    CONSTRAINT uq_sys_file_object_name UNIQUE (object_name)
);

CREATE TABLE sys_group
(
    id            VARCHAR(64) NOT NULL,
    name          VARCHAR(64) NOT NULL,
    owner_dept_id VARCHAR(64),
    description   TEXT,
    status        VARCHAR(32) NOT NULL,
    extra         JSON        NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_group_name UNIQUE (name)
);

CREATE TABLE sys_iam_relation
(
    id                    VARCHAR(64)  NOT NULL,
    subject_type          VARCHAR(32)  NOT NULL,
    subject_id            VARCHAR(64)  NOT NULL,
    account_type          VARCHAR(32)  NOT NULL,
    relation_type         VARCHAR(64)  NOT NULL,
    target_type           VARCHAR(32)  NOT NULL,
    target_id             VARCHAR(64)  NOT NULL,
    target_key            VARCHAR(128) NOT NULL,
    grant_mode            VARCHAR(32)  NOT NULL,
    data_scope            VARCHAR(32)  NOT NULL,
    custom_scope_dept_ids JSON         NOT NULL DEFAULT '[]'::json,
    is_primary            BOOLEAN      NOT NULL DEFAULT FALSE,
    sort                  INTEGER      NOT NULL DEFAULT 0,
    status                VARCHAR(32)  NOT NULL,
    description           TEXT,
    reason                TEXT,
    expired_at            TIMESTAMPTZ,
    extra                 JSON         NOT NULL DEFAULT '{}'::json,
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by            VARCHAR(64),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by            VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_iam_relation_subject_relation_target UNIQUE (subject_type, subject_id, relation_type, target_type,
                                                                   target_id, target_key, account_type)
);

CREATE TABLE sys_operation_audit_log (
                                         id            VARCHAR(64) NOT NULL,
                                         module        VARCHAR(64) NOT NULL,
                                         resource_type VARCHAR(128),
                                         resource_id   VARCHAR(128),
                                         action        VARCHAR(64) NOT NULL,
                                         summary       VARCHAR(255),
                                         before_data   JSON,
                                         after_data    JSON,
                                         account_id    VARCHAR(64),
                                         account_type  VARCHAR(32),
                                         request_id    VARCHAR(64),
                                         ip            VARCHAR(64),
                                         user_agent    VARCHAR(512),
                                         success       BOOLEAN     NOT NULL DEFAULT FALSE,
                                         error_message TEXT,
                                         created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
                                         PRIMARY KEY (id)
);

CREATE TABLE sys_operation_audit_outbox
(
    id         VARCHAR(64) NOT NULL,
    payload    TEXT        NOT NULL,
    status     VARCHAR(32) NOT NULL,
    attempts   INTEGER     NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    claimed_at TIMESTAMPTZ,
    PRIMARY KEY (id)
);

CREATE TABLE sys_position
(
    id            VARCHAR(64) NOT NULL,
    name          VARCHAR(64) NOT NULL,
    category      VARCHAR(32) NOT NULL,
    owner_dept_id VARCHAR(64),
    sort          INTEGER     NOT NULL DEFAULT 0,
    is_virtual    BOOLEAN     NOT NULL DEFAULT FALSE,
    status        VARCHAR(32) NOT NULL,
    description   TEXT,
    extra         JSON        NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE TABLE sys_resource
(
    id            VARCHAR(64) NOT NULL,
    parent_id     VARCHAR(64),
    code          VARCHAR(64) NOT NULL,
    name          VARCHAR(64) NOT NULL,
    resource_type VARCHAR(32) NOT NULL,
    module_id     VARCHAR(64),
    path          VARCHAR(255),
    component     VARCHAR(255),
    redirect      VARCHAR(255),
    icon          VARCHAR(255),
    color         VARCHAR(32),
    href          VARCHAR(255),
    sort          INTEGER     NOT NULL DEFAULT 0,
    is_visible    BOOLEAN     NOT NULL DEFAULT FALSE,
    is_cache      BOOLEAN     NOT NULL DEFAULT FALSE,
    is_affix      BOOLEAN     NOT NULL DEFAULT FALSE,
    status        VARCHAR(32) NOT NULL,
    description   TEXT,
    layout        VARCHAR(255),
    extra         JSON        NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_resource_module_id_code UNIQUE (module_id, code)
);

CREATE TABLE sys_resource_module
(
    id          VARCHAR(64) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    code        VARCHAR(64) NOT NULL,
    client      VARCHAR(32) NOT NULL,
    icon        VARCHAR(255),
    color       VARCHAR(32),
    sort        INTEGER     NOT NULL DEFAULT 0,
    status      VARCHAR(32) NOT NULL,
    description TEXT,
    extra       JSON        NOT NULL DEFAULT '{}'::json,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by  VARCHAR(64),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by  VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_resource_module_code UNIQUE (code)
);

CREATE TABLE sys_role
(
    id            VARCHAR(64) NOT NULL,
    code          VARCHAR(64) NOT NULL,
    name          VARCHAR(64) NOT NULL,
    category      VARCHAR(64) NOT NULL,
    scope_type    VARCHAR(32) NOT NULL,
    owner_dept_id VARCHAR(64),
    sort          INTEGER     NOT NULL DEFAULT 0,
    status        VARCHAR(32) NOT NULL,
    is_builtin    BOOLEAN     NOT NULL DEFAULT FALSE,
    description   TEXT,
    extra         JSON        NOT NULL DEFAULT '{}'::json,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by    VARCHAR(64),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_by    VARCHAR(64),
    PRIMARY KEY (id),
    CONSTRAINT uq_sys_role_code UNIQUE (code)
);

CREATE TABLE sys_weak_password
(
    id         VARCHAR(64)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by VARCHAR(64),
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_by VARCHAR(64),
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_pwd_history_account_created ON sys_account_password_history (account_id, created_at);
CREATE INDEX IF NOT EXISTS ix_cg_test_activity_owner_dept_id ON cg_test_activity (owner_dept_id);
CREATE INDEX IF NOT EXISTS ix_cg_test_catalog_owner_dept_id ON cg_test_catalog (owner_dept_id);
CREATE INDEX IF NOT EXISTS ix_cg_test_knowledge_category_owner_dept_id ON cg_test_knowledge_category (owner_dept_id);
CREATE INDEX IF NOT EXISTS ix_cg_test_order_owner_dept_id ON cg_test_order (owner_dept_id);
CREATE INDEX IF NOT EXISTS ix_sys_banner_position_status_sort ON sys_banner (position, status, sort);
CREATE INDEX IF NOT EXISTS ix_sys_codegen_field_plan_role_sort ON sys_codegen_field (plan_id, table_role, sort);
CREATE INDEX IF NOT EXISTS ix_sys_codegen_plan_gen_type ON sys_codegen_plan (gen_type);
CREATE INDEX IF NOT EXISTS ix_sys_codegen_plan_main_table ON sys_codegen_plan (main_table);
CREATE INDEX IF NOT EXISTS idx_sys_config_category ON sys_config (category);
CREATE INDEX IF NOT EXISTS idx_sys_config_category_scope_scene ON sys_config (category, scope, scene);
CREATE UNIQUE INDEX IF NOT EXISTS idx_sys_config_key ON sys_config (config_key);
CREATE INDEX IF NOT EXISTS idx_sys_dict_category ON sys_dict (category);
CREATE UNIQUE INDEX IF NOT EXISTS idx_sys_dict_code ON sys_dict (code);
CREATE INDEX IF NOT EXISTS idx_sys_dict_parent_id ON sys_dict (parent_id);
CREATE INDEX IF NOT EXISTS ix_sys_iam_relation_account_type_relation ON sys_iam_relation (account_type, relation_type);
CREATE INDEX IF NOT EXISTS ix_sys_iam_relation_subject ON sys_iam_relation (subject_type, subject_id, relation_type);
CREATE INDEX IF NOT EXISTS ix_sys_iam_relation_target ON sys_iam_relation (target_type, target_id, target_key);
CREATE INDEX IF NOT EXISTS idx_sys_operation_audit_account_id ON sys_operation_audit_log (account_id);
CREATE INDEX IF NOT EXISTS idx_sys_operation_audit_created_at ON sys_operation_audit_log (created_at);
CREATE INDEX IF NOT EXISTS idx_sys_operation_audit_module_action ON sys_operation_audit_log (module, action);
CREATE INDEX IF NOT EXISTS idx_sys_operation_audit_resource ON sys_operation_audit_log (resource_type, resource_id);
CREATE UNIQUE INDEX IF NOT EXISTS idx_sys_weak_password_password ON sys_weak_password (password);
