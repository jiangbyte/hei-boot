-- Bootstrap seed extracted from hei-fastapi scripts/db/seed/data.sql
-- Structural tables only (FK-safe order). Demo/portal/cg_test data omitted.
-- Password for superadmin: 123456 (Java BCrypt).

-- sys_resource_module: 1 rows
INSERT INTO sys_resource_module (id, name, code, client, icon, color, sort, status, description, extra)
VALUES ('210001', '管理端', 'admin', 'ADMIN', 'icon-park-outline:all-application', '#2080f0', 1, 'ENABLED',
        '管理端菜单与权限资源模块', '{}'::json);

-- sys_resource: 157 rows
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200041', NULL, 'client-resource-auth', '客户端资源授权', 'CATALOG', '210001', '/client-resource-auth', NULL,
        NULL, 'icon-park-outline:application-one', NULL, NULL, 16, TRUE, FALSE, FALSE, 'ENABLED',
        '客户端模块与客户端资源授权配置', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200007', '200006', 'iam-account', '账号管理', 'MENU', '210001', '/iam/account', '/iam/account/index.vue', NULL,
        'icon-park-outline:people', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200008', '200006', 'iam-dept', '部门管理', 'MENU', '210001', '/iam/dept', '/iam/dept/index.vue', NULL,
        'icon-park-outline:tree-diagram', NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200006', NULL, 'org', '组织权限', 'CATALOG', '210001', '/iam', NULL, NULL, 'icon-park-outline:people', NULL,
        NULL, 10, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200001', NULL, 'dashboard', '运营工作台', 'MENU', '210001', '/dashboard', '/dashboard/index.vue', NULL,
        'icon-park-outline:analysis', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201101', '200007', 'iam-account-create', '新增账号', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201102', '200007', 'iam-account-detail', '查看账号', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201103', '200007', 'iam-account-update', '编辑账号', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201104', '200007', 'iam-account-delete', '删除账号', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201105', '200007', 'iam-account-grant-role', '分配角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 5, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201106', '200007', 'iam-account-grant-group', '分配用户组', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 6, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201107', '200007', 'iam-account-grant-dept', '分配部门', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 7, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201108', '200007', 'iam-account-grant-resource', '分配资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 8, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201153', '200010', 'iam-position-update', '编辑岗位', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201154', '200010', 'iam-position-delete', '删除岗位', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201161', '200011', 'iam-role-create', '新增角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201162', '200011', 'iam-role-detail', '查看角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201163', '200011', 'iam-role-update', '编辑角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201164', '200011', 'iam-role-delete', '删除角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201165', '200011', 'iam-role-grant-resource', '分配资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 5, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201167', '200011', 'iam-role-grant-user', '分配用户', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        7, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200011', '200006', 'iam-role', '角色管理', 'MENU', '210001', '/iam/role', '/iam/role/index.vue', NULL,
        'icon-park-outline:peoples', NULL, NULL, 5, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200009', '200006', 'iam-group', '用户组管理', 'MENU', '210001', '/iam/group', '/iam/group/index.vue', NULL,
        'icon-park-outline:group', NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200010', '200006', 'iam-position', '岗位管理', 'MENU', '210001', '/iam/position', '/iam/position/index.vue',
        NULL, 'icon-park-outline:people-bottom', NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200031', '200041', 'iam-clientmodule', '客户端模块管理', 'MENU', '210001', '/iam/client_module',
        '/iam/client_module/index.vue', NULL, 'icon-park-outline:application-one', NULL, NULL, 1, TRUE, FALSE, FALSE,
        'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200032', '200041', 'iam-clientresource', '客户端资源管理', 'MENU', '210001', '/iam/client_resource',
        '/iam/client_resource/index.vue', NULL, 'icon-park-outline:page-template', NULL, NULL, 2, TRUE, FALSE, FALSE,
        'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200012', '200040', 'iam-resource', '资源管理', 'MENU', '210001', '/iam/resource', '/iam/resource/index.vue',
        NULL, 'icon-park-outline:all-application', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200018', '200040', 'iam-resourcemodule', '资源模块管理', 'MENU', '210001', '/iam/resource_module',
        '/iam/resource_module/index.vue', NULL, 'icon-park-outline:blocks-and-arrows', NULL, NULL, 2, TRUE, FALSE,
        FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201109', '200007', 'iam-account-grant-client-resource', '分配客户端资源', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 9, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201121', '200008', 'iam-dept-create', '新增部门', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201122', '200008', 'iam-dept-detail', '查看部门', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201123', '200008', 'iam-dept-update', '编辑部门', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201124', '200008', 'iam-dept-delete', '删除部门', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201131', '200009', 'iam-group-create', '新增用户组', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201132', '200009', 'iam-group-detail', '查看用户组', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201133', '200009', 'iam-group-update', '编辑用户组', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201134', '200009', 'iam-group-delete', '删除用户组', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201135', '200009', 'iam-group-grant-user', '分配用户', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        5, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201136', '200009', 'iam-group-grant-role', '分配角色', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        6, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201137', '200009', 'iam-group-grant-resource', '分配资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 7, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201138', '200009', 'iam-group-grant-client-resource', '分配客户端资源', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 8, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201151', '200010', 'iam-position-create', '新增岗位', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201152', '200010', 'iam-position-detail', '查看岗位', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201168', '200011', 'iam-role-grant-client-resource', '分配客户端资源', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 8, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201181', '200012', 'iam-resource-create', '新增资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201182', '200012', 'iam-resource-detail', '查看资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201183', '200012', 'iam-resource-update', '编辑资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201184', '200012', 'iam-resource-delete', '删除资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201185', '200012', 'iam-resource-grant', '绑定权限', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 5,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201191', '200018', 'iam-resourcemodule-create', '新增资源模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201192', '200018', 'iam-resourcemodule-detail', '查看资源模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201193', '200018', 'iam-resourcemodule-update', '编辑资源模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201194', '200018', 'iam-resourcemodule-delete', '删除资源模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201311', '200031', 'iam-clientmodule-create', '新增客户端模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201312', '200031', 'iam-clientmodule-detail', '查看客户端模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201313', '200031', 'iam-clientmodule-update', '编辑客户端模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201314', '200031', 'iam-clientmodule-delete', '删除客户端模块', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201321', '200032', 'iam-clientresource-create', '新增客户端资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201322', '200032', 'iam-clientresource-detail', '查看客户端资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201323', '200032', 'iam-clientresource-update', '编辑客户端资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201324', '200032', 'iam-clientresource-delete', '删除客户端资源', 'BUTTON', '210001', NULL, NULL, NULL, NULL,
        NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201325', '200032', 'iam-clientresource-list', '客户端资源树', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 5, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201326', '200032', 'iam-clientresource-grant', '绑定客户端资源权限', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 6, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202201', '202200', 'message-notice-page', '分页消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202202', '202200', 'message-notice-create', '新增消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202203', '202200', 'message-notice-detail', '详情消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202204', '202200', 'message-notice-update', '编辑消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202205', '202200', 'message-notice-delete', '删除消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202209', '202200', 'message-notice-publish', '发布消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 55, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202240', '202200', 'message-notice-revoke', '撤回消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        56, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202241', '202200', 'message-notice-pin', '置顶消息', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        57, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202206', '202200', 'message-notice-create-page', '新增消息页', 'PAGE', '210001', '/message/notice/create',
        '/message/notice/form.vue', NULL, NULL, NULL, NULL, 60, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202207', '202200', 'message-notice-edit-page', '编辑消息页', 'PAGE', '210001', '/message/notice/edit',
        '/message/notice/form.vue', NULL, NULL, NULL, NULL, 70, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202208', '202200', 'message-notice-detail-page', '消息详情页', 'PAGE', '210001', '/message/notice/detail',
        '/message/notice/detail.vue', NULL, NULL, NULL, NULL, 80, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202221', '202220', 'message-feedback-page', '分页反馈', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202222', '202220', 'message-feedback-detail', '查看反馈', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202223', '202220', 'message-feedback-update', '处理反馈', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202224', '202220', 'message-feedback-delete', '删除反馈', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202225', '202220', 'message-feedback-edit-page', '处理反馈页', 'PAGE', '210001', '/message/feedback/edit',
        '/message/feedback/form.vue', NULL, NULL, NULL, NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202226', '202220', 'message-feedback-detail-page', '反馈详情页', 'PAGE', '210001', '/message/feedback/detail',
        '/message/feedback/detail.vue', NULL, NULL, NULL, NULL, 60, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201041', '200025', 'sys-session-tokenlist', '查看令牌', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201042', '200025', 'sys-session-exit', '强退账号', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201043', '200025', 'sys-session-tokenexit', '强退令牌', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201011', '200004', 'sys-dict-create', '新增字典', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201012', '200004', 'sys-dict-detail', '查看字典', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201013', '200004', 'sys-dict-update', '编辑字典', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201014', '200004', 'sys-dict-delete', '删除字典', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201021', '200005', 'sys-banner-create', '新增展示图', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201022', '200005', 'sys-banner-detail', '查看展示图', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201057', '202015', 'sys-codegen-download', '下载代码', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        70, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203011', '202004', 'biz-cgtestactivity-page', '分页活动', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203012', '202004', 'biz-cgtestactivity-create', '新增活动', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203013', '202004', 'biz-cgtestactivity-detail', '详情活动', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203014', '202004', 'biz-cgtestactivity-update', '编辑活动', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203015', '202004', 'biz-cgtestactivity-delete', '删除活动', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201023', '200005', 'sys-banner-update', '编辑展示图', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201024', '200005', 'sys-banner-delete', '删除展示图', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201025', '200005', 'sys-banner-create-page', '新增展示图页', 'PAGE', '210001', '/sys/banner/create',
        '/sys/banner/form.vue', NULL, NULL, NULL, NULL, 5, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201026', '200005', 'sys-banner-edit-page', '编辑展示图页', 'PAGE', '210001', '/sys/banner/edit',
        '/sys/banner/form.vue', NULL, NULL, NULL, NULL, 6, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200023', '200003', 'sys-file', '文件管理', 'MENU', '210001', '/sys/file', '/sys/file/index.vue', NULL,
        'icon-park-outline:file-code', NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202010', '200003', 'system-config', '系统配置', 'MENU', '210001', '/sys/config', '/sys/config/index.vue', NULL,
        'icon-park-outline:setting-config', NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', '系统配置管理页面', NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200027', '200003', 'sys-audit-api', '操作审计接口', 'API_GROUP', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        6, TRUE, FALSE, FALSE, 'ENABLED', '操作审计后端权限组', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202015', '202001', 'sys-codegen', '代码生成', 'MENU', '210001', '/sys/codegen', '/sys/codegen/index.vue', NULL,
        'icon-park-outline:code', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', '代码生成管理', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201027', '200005', 'sys-banner-detail-page', '展示图详情页', 'PAGE', '210001', '/sys/banner/detail',
        '/sys/banner/detail.vue', NULL, NULL, NULL, NULL, 7, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201031', '200023', 'sys-file-upload', '上传文件', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 1,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201032', '200023', 'sys-file-detail', '查看文件', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 2,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201033', '200023', 'sys-file-update', '编辑文件', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 3,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201034', '200023', 'sys-file-url', '打开文件', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 4, TRUE,
        FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201035', '200023', 'sys-file-delete', '删除文件', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL, 5,
        TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202011', '202010', 'sys:config:create', '新增系统配置', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202012', '202010', 'sys:config:detail', '查看系统配置', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202013', '202010', 'sys:config:update', '编辑系统配置', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        3, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202014', '202010', 'sys:config:delete', '删除系统配置', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        4, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201051', '202015', 'sys-codegen-create', '新增生成方案', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201052', '202015', 'sys-codegen-detail', '查看生成方案', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201053', '202015', 'sys-codegen-update', '编辑生成方案', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201054', '202015', 'sys-codegen-delete', '删除生成方案', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201055', '202015', 'sys-codegen-tables', '读取数据库表', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201056', '202015', 'sys-codegen-preview', '预览代码', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        60, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203021', '202005', 'biz-cgtestcatalog-page', '分页目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203022', '202005', 'biz-cgtestcatalog-create', '新增目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203023', '202005', 'biz-cgtestcatalog-detail', '详情目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203024', '202005', 'biz-cgtestcatalog-update', '编辑目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203025', '202005', 'biz-cgtestcatalog-delete', '删除目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203026', '202005', 'biz-cgtestcatalog-list', '树列表目录', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 90, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203031', '202006', 'biz-cgtestorder-page', '分页订单', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203032', '202006', 'biz-cgtestorder-create', '新增订单', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203033', '202006', 'biz-cgtestorder-detail', '详情订单', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203034', '202006', 'biz-cgtestorder-update', '编辑订单', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203035', '202006', 'biz-cgtestorder-delete', '删除订单', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203041', '202007', 'biz-cgtestknowledgecategory-page', '分页知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 10, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203042', '202007', 'biz-cgtestknowledgecategory-create', '新增知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 20, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203043', '202007', 'biz-cgtestknowledgecategory-detail', '详情知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 30, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203044', '202007', 'biz-cgtestknowledgecategory-update', '编辑知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 40, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203045', '202007', 'biz-cgtestknowledgecategory-delete', '删除知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 50, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('203046', '202007', 'biz-cgtestknowledgecategory-list', '树列表知识分类', 'BUTTON', '210001', NULL, NULL, NULL,
        NULL, NULL, NULL, 90, FALSE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200040', NULL, 'resource-auth', '资源授权', 'CATALOG', '210001', '/resource-auth', NULL, NULL,
        'icon-park-outline:all-application', NULL, NULL, 15, TRUE, FALSE, FALSE, 'ENABLED',
        '菜单资源与资源模块授权配置', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200005', '200019', 'content-banner', '展示图管理', 'MENU', '210001', '/sys/banner', '/sys/banner/index.vue',
        NULL, 'icon-park-outline:ad-product', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202220', '200019', 'content-feedback', '反馈管理', 'MENU', '210001', '/message/feedback',
        '/message/feedback/index.vue', NULL, 'icon-park-outline:write', NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED',
        '意见反馈管理', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201060', '200028', 'sys-login-log-detail', '查看登录日志', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL,
        NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('201061', '200029', 'sys-audit-detail', '查看审计详情', 'BUTTON', '210001', NULL, NULL, NULL, NULL, NULL, NULL,
        1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200025', '200003', 'sys-session', '在线会话', 'MENU', '210001', '/sys/session', '/auth/session/index.vue',
        NULL, 'icon-park-outline:connection', NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200004', '200003', 'sys-dict', '字典管理', 'MENU', '210001', '/sys/dict', '/sys/dict/index.vue', NULL,
        'icon-park-outline:file-search', NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200028', '200003', 'sys-login-log', '登录日志', 'MENU', '210001', '/sys/login-log', '/sys/login-log/index.vue',
        NULL, 'icon-park-outline:log', NULL, NULL, 5, TRUE, FALSE, FALSE, 'ENABLED', '登录成功/失败历史记录', NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200029', '200003', 'sys-audit', '操作审计', 'MENU', '210001', '/sys/audit', '/sys/audit/index.vue', NULL,
        'icon-park-outline:audit', NULL, NULL, 7, TRUE, FALSE, FALSE, 'ENABLED', '系统操作审计日志', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202030', NULL, 'biz-demo', '业务示例', 'CATALOG', '210001', '/biz', NULL, NULL,
        'icon-park-outline:application-one', NULL, NULL, 40, TRUE, FALSE, FALSE, 'ENABLED', '代码生成业务示例页面',
        NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202004', '202030', 'biz-cgtestactivity', '代码生成测试-活动', 'MENU', '210001', '/biz/cg-test-activity',
        '/biz/cg-test-activity/index.vue', NULL, 'icon-park-outline:calendar', NULL, NULL, 1, TRUE, FALSE, FALSE,
        'ENABLED', '代码生成 CRUD 样例', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202005', '202030', 'biz-cgtestcatalog', '代码生成测试-目录树', 'MENU', '210001', '/biz/cg-test-catalog',
        '/biz/cg-test-catalog/index.vue', NULL, 'icon-park-outline:tree-list', NULL, NULL, 2, TRUE, FALSE, FALSE,
        'ENABLED', '代码生成树表样例', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202006', '202030', 'biz-cgtestorder', '代码生成测试-订单', 'MENU', '210001', '/biz/cg-test-order',
        '/biz/cg-test-order/index.vue', NULL, 'icon-park-outline:transaction-order', NULL, NULL, 3, TRUE, FALSE, FALSE,
        'ENABLED', '代码生成主子表样例', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202007', '202030', 'biz-cgtestknowledgecategory', '代码生成测试-知识分类', 'MENU', '210001',
        '/biz/cg-test-knowledge-category', '/biz/cg-test-knowledge-category/index.vue', NULL,
        'icon-park-outline:book-open', NULL, NULL, 4, TRUE, FALSE, FALSE, 'ENABLED', '代码生成左树右表样例', NULL,
        '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202002', '202001', 'system-test-editor', '编辑器测试', 'MENU', '210001', '/test/editor',
        '/test/editor/index.vue', NULL, 'icon-park-outline:edit', NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED',
        'Markdown、富文本和代码编辑器组件测试页面', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202003', '202001', 'system-test-icon', '图标选择器测试', 'MENU', '210001', '/test/icon',
        '/test/icon/index.vue', NULL, 'icon-park-outline:all-application', NULL, NULL, 3, TRUE, FALSE, FALSE, 'ENABLED',
        'Iconify 离线图标选择器测试页面', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200003', NULL, 'ops', '系统运维', 'CATALOG', '210001', '/sys', NULL, NULL, 'icon-park-outline:setting-two',
        NULL, NULL, 25, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('200019', NULL, 'content', '内容运营', 'CATALOG', '210001', '/content', NULL, '/message/notice',
        'icon-park-outline:picture-album', NULL, NULL, 20, TRUE, FALSE, FALSE, 'ENABLED', NULL, NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202200', '200019', 'content-notice', '通知消息', 'MENU', '210001', '/message/notice',
        '/message/notice/index.vue', NULL, 'icon-park-outline:message', NULL, NULL, 2, TRUE, FALSE, FALSE, 'ENABLED',
        '消息管理', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202230', '200019', 'content-manage', '消息管理', 'CATALOG', '210001', '/message/manage', NULL, NULL,
        'icon-park-outline:list-view', NULL, NULL, 6, FALSE, FALSE, FALSE, 'ENABLED', '消息管理', NULL, '{}'::json);
INSERT INTO sys_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon, color,
                          href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('202001', NULL, 'devtools', '开发工具', 'CATALOG', '210001', '/test', NULL, '/sys/codegen',
        'icon-park-outline:code', NULL, NULL, 90, TRUE, FALSE, FALSE, 'ENABLED', '系统模块测试页面目录', NULL,
        '{}'::json);

-- sys_client_module: 2 rows
INSERT INTO sys_client_module (id, name, code, account_type, icon, color, sort, status, description, extra)
VALUES ('221001', '管理端默认模块', 'admin-default', 'ADMIN', 'icon-park-outline:application-one', NULL, 1, 'ENABLED',
        '管理端默认客户端模块', '{}'::json);
INSERT INTO sys_client_module (id, name, code, account_type, icon, color, sort, status, description, extra)
VALUES ('221002', '门户端默认模块', 'portal-default', 'PORTAL', 'icon-park-outline:application-one', NULL, 1, 'ENABLED',
        '门户端默认客户端模块', '{}'::json);

-- sys_client_resource: 2 rows
INSERT INTO sys_client_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon,
                                 color, href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('222001', NULL, 'home', '首页', 'MENU', '221001', '/home', '/home/index.vue', NULL, 'icon-park-outline:home',
        NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', '管理端客户端样例菜单', NULL, '{}'::json);
INSERT INTO sys_client_resource (id, parent_id, code, name, resource_type, module_id, path, component, redirect, icon,
                                 color, href, sort, is_visible, is_cache, is_affix, status, description, layout, extra)
VALUES ('222002', NULL, 'home', '首页', 'MENU', '221002', '/home', '/home/index.vue', NULL, 'icon-park-outline:home',
        NULL, NULL, 1, TRUE, FALSE, FALSE, 'ENABLED', '门户端客户端样例菜单', NULL, '{}'::json);

-- sys_dict: 109 rows
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100017', 'RESOURCE_TYPE', '资源类型', 'RESOURCE_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100024', 'DATA_SCOPE', '数据范围', 'DATA_SCOPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100040', 'DEPT_CATEGORY', '部门分类', 'DEPT_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100045', 'POSITION_CATEGORY', '岗位分类', 'POSITION_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100054', 'BANNER_CATEGORY', '展示图分类', 'BANNER_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100061', 'BANNER_TYPE', '展示图类型', 'BANNER_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100068', 'BANNER_POSITION', '展示图位置', 'BANNER_POSITION', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100077', 'BANNER_LINK_TYPE', '展示图链接类型', 'BANNER_LINK_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100001', 'COMMON_STATUS', '状态', 'COMMON_STATUS', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100004', 'SYS_BIZ_CATEGORY', '系统/业务分类', 'SYS_BIZ_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100010', 'ACCOUNT_STATUS', '账号状态', 'ACCOUNT_STATUS', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100014', 'ROLE_SCOPE_TYPE', '角色范围类型', 'ROLE_SCOPE_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100085', 'ACCOUNT_IDENTITY_BIND_STATUS', '账号身份绑定状态', 'ACCOUNT_IDENTITY_BIND_STATUS', '#2080f0', 'SYS',
        NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100095', 'NOTIFICATION_SEVERITY', '通知严重级别', 'NOTIFICATION_SEVERITY', '#2080f0', 'SYS', NULL, 'ENABLED',
        0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100127', 'CONTENT_TYPE', '内容格式', 'CONTENT_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100131', 'TARGET_SCOPE', '目标范围', 'TARGET_SCOPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100136', 'NOTIFY_LOCATION', '通知位置', 'NOTIFY_LOCATION', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100140', 'FEEDBACK_CATEGORY', '反馈分类', 'FEEDBACK_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100144', 'FEEDBACK_STATUS', '反馈状态', 'FEEDBACK_STATUS', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100149', 'PUBLISH_STATUS', '发布状态', 'PUBLISH_STATUS', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100210', 'NOTIFICATION_CATEGORY', '通知分类', 'NOTIFICATION_CATEGORY', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100002', 'COMMON_STATUS_ENABLED', '启用', 'ENABLED', '#18a058', 'SYS', '100001', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100005', 'SYS_BIZ_CATEGORY_SYS', '系统', 'SYS', '#2080f0', 'SYS', '100004', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100011', 'ACCOUNT_STATUS_ENABLED', '启用', 'ENABLED', '#18a058', 'SYS', '100010', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100015', 'ROLE_SCOPE_TYPE_PLATFORM', '平台', 'PLATFORM', '#2080f0', 'SYS', '100014', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100018', 'RESOURCE_TYPE_CATALOG', '目录', 'CATALOG', '#722ed1', 'SYS', '100017', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100025', 'DATA_SCOPE_ALL', '全部', 'ALL', '#18a058', 'SYS', '100024', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100041', 'DEPT_CATEGORY_COMPANY', '公司', 'COMPANY', '#2080f0', 'SYS', '100040', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100046', 'POSITION_CATEGORY_MANAGEMENT', '管理', 'MANAGEMENT', '#2080f0', 'SYS', '100045', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100055', 'BANNER_CATEGORY_HOME', '首页', 'HOME', '#18a058', 'SYS', '100054', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100062', 'BANNER_TYPE_CAROUSEL', '轮播图', 'CAROUSEL', '#18a058', 'SYS', '100061', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100069', 'BANNER_POSITION_HOME_TOP', '首页顶部', 'HOME_TOP', '#18a058', 'SYS', '100068', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100078', 'BANNER_LINK_TYPE_URL', '外部链接', 'URL', '#18a058', 'SYS', '100077', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100086', 'ACCOUNT_IDENTITY_BIND_STATUS_BOUND', '已绑定', 'BOUND', '#18a058', 'SYS', '100085', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100096', 'NOTIFICATION_SEVERITY_INFO', '信息', 'INFO', '#2080f0', 'SYS', '100095', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100128', 'CONTENT_TYPE_TEXT', '纯文本', 'text', '#909399', 'SYS', '100127', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100132', 'TARGET_SCOPE_ALL', '全部', 'ALL', '#2080f0', 'SYS', '100131', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100137', 'NOTIFY_LOCATION_CENTER', '通知中心', 'center', '#2080f0', 'SYS', '100136', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100141', 'FEEDBACK_CATEGORY_SUGGESTION', '功能建议', 'SUGGESTION', '#18a058', 'SYS', '100140', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100145', 'FEEDBACK_STATUS_PENDING', '待处理', 'PENDING', '#f0a020', 'SYS', '100144', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100150', 'PUBLISH_STATUS_DRAFT', '草稿', 'DRAFT', '#909399', 'SYS', '100149', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100211', 'NOTIFICATION_CATEGORY_ORDER', '订单', 'ORDER', '#2080f0', 'SYS', '100210', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100003', 'COMMON_STATUS_DISABLED', '禁用', 'DISABLED', '#d03050', 'SYS', '100001', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100006', 'SYS_BIZ_CATEGORY_BIZ', '业务', 'BIZ', '#f0a020', 'SYS', '100004', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100012', 'ACCOUNT_STATUS_DISABLED', '禁用', 'DISABLED', '#d03050', 'SYS', '100010', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100016', 'ROLE_SCOPE_TYPE_DEPT', '部门', 'DEPT', '#18a058', 'SYS', '100014', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100019', 'RESOURCE_TYPE_MENU', '菜单', 'MENU', '#2080f0', 'SYS', '100017', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100026', 'DATA_SCOPE_DEPT_AND_CHILD', '本部门及子部门', 'DEPT_AND_CHILD', '#2080f0', 'SYS', '100024',
        'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100042', 'DEPT_CATEGORY_DEPARTMENT', '部门', 'DEPARTMENT', '#18a058', 'SYS', '100040', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100047', 'POSITION_CATEGORY_TECHNICAL', '技术', 'TECHNICAL', '#18a058', 'SYS', '100045', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100056', 'BANNER_CATEGORY_LOGIN', '登录', 'LOGIN', '#2080f0', 'SYS', '100054', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100063', 'BANNER_TYPE_HERO', '主视觉', 'HERO', '#2080f0', 'SYS', '100061', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100070', 'BANNER_POSITION_HOME_MIDDLE', '首页中部', 'HOME_MIDDLE', '#18a058', 'SYS', '100068', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100079', 'BANNER_LINK_TYPE_ROUTE', '路由', 'ROUTE', '#2080f0', 'SYS', '100077', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100087', 'ACCOUNT_IDENTITY_BIND_STATUS_UNBOUND', '未绑定', 'UNBOUND', '#909399', 'SYS', '100085', 'ENABLED',
        2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100097', 'NOTIFICATION_SEVERITY_SUCCESS', '成功', 'SUCCESS', '#18a058', 'SYS', '100095', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100129', 'CONTENT_TYPE_HTML', '富文本', 'html', '#18a058', 'SYS', '100127', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100138', 'NOTIFY_LOCATION_POPUP', '弹窗', 'popup', '#f0a020', 'SYS', '100136', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100142', 'FEEDBACK_CATEGORY_BUG', '问题反馈', 'BUG', '#d03050', 'SYS', '100140', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100146', 'FEEDBACK_STATUS_REVIEWED', '已查看', 'REVIEWED', '#2080f0', 'SYS', '100144', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100151', 'PUBLISH_STATUS_PUBLISHED', '已发布', 'PUBLISHED', '#18a058', 'SYS', '100149', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100206', 'TARGET_SCOPE_ACCOUNT_TYPE', '按账号类型', 'ACCOUNT_TYPE', '#722ed1', 'SYS', '100131', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100212', 'NOTIFICATION_CATEGORY_APPROVAL', '审批', 'APPROVAL', '#722ed1', 'SYS', '100210', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100013', 'ACCOUNT_STATUS_CANCELLED', '已注销', 'CANCELLED', '#909399', 'SYS', '100010', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100020', 'RESOURCE_TYPE_PAGE', '页面', 'PAGE', '#18a058', 'SYS', '100017', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100027', 'DATA_SCOPE_DEPT', '本部门', 'DEPT', '#2db7f5', 'SYS', '100024', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100043', 'DEPT_CATEGORY_TEAM', '团队', 'TEAM', '#f0a020', 'SYS', '100040', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100048', 'POSITION_CATEGORY_OPERATION', '运营', 'OPERATION', '#f0a020', 'SYS', '100045', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100057', 'BANNER_CATEGORY_WORKPLACE', '工作台', 'WORKPLACE', '#722ed1', 'SYS', '100054', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100064', 'BANNER_TYPE_NOTICE', '公告', 'NOTICE', '#f0a020', 'SYS', '100061', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100071', 'BANNER_POSITION_HOME_BOTTOM', '首页底部', 'HOME_BOTTOM', '#18a058', 'SYS', '100068', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100080', 'BANNER_LINK_TYPE_NONE', '无链接', 'NONE', '#909399', 'SYS', '100077', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100098', 'NOTIFICATION_SEVERITY_WARNING', '警告', 'WARNING', '#f0a020', 'SYS', '100095', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100130', 'CONTENT_TYPE_MARKDOWN', 'Markdown', 'markdown', '#722ed1', 'SYS', '100127', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100135', 'TARGET_SCOPE_SPECIFIC', '指定用户', 'SPECIFIC', '#d03050', 'SYS', '100131', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100139', 'NOTIFY_LOCATION_DASHBOARD', '工作台公告区', 'dashboard', '#722ed1', 'SYS', '100136', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100143', 'FEEDBACK_CATEGORY_OTHER', '其他', 'OTHER', '#909399', 'SYS', '100140', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100147', 'FEEDBACK_STATUS_RESOLVED', '已解决', 'RESOLVED', '#18a058', 'SYS', '100144', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100152', 'PUBLISH_STATUS_REVOKED', '已撤回', 'REVOKED', '#d03050', 'SYS', '100149', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100213', 'NOTIFICATION_CATEGORY_SYSTEM', '系统', 'SYSTEM', '#18a058', 'SYS', '100210', 'ENABLED', 3);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100021', 'RESOURCE_TYPE_BUTTON', '按钮', 'BUTTON', '#f0a020', 'SYS', '100017', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100028', 'DATA_SCOPE_SELF', '本人', 'SELF', '#f0a020', 'SYS', '100024', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100044', 'DEPT_CATEGORY_VIRTUAL', '虚拟组织', 'VIRTUAL', '#909399', 'SYS', '100040', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100049', 'POSITION_CATEGORY_SUPPORT', '支持', 'SUPPORT', '#909399', 'SYS', '100045', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100058', 'BANNER_CATEGORY_NOTICE', '公告', 'NOTICE', '#f0a020', 'SYS', '100054', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100065', 'BANNER_TYPE_CARD', '卡片', 'CARD', '#722ed1', 'SYS', '100061', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100072', 'BANNER_POSITION_LOGIN_SIDE', '登录侧边', 'LOGIN_SIDE', '#2080f0', 'SYS', '100068', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100099', 'NOTIFICATION_SEVERITY_ERROR', '错误', 'ERROR', '#d03050', 'SYS', '100095', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100148', 'FEEDBACK_STATUS_CLOSED', '已关闭', 'CLOSED', '#909399', 'SYS', '100144', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100214', 'NOTIFICATION_CATEGORY_SECURITY', '安全', 'SECURITY', '#d03050', 'SYS', '100210', 'ENABLED', 4);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100022', 'RESOURCE_TYPE_ACTION', '操作', 'ACTION', '#d03050', 'SYS', '100017', 'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100029', 'DATA_SCOPE_CUSTOM', '自定义部门', 'CUSTOM', '#722ed1', 'SYS', '100024', 'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100059', 'BANNER_CATEGORY_ADMIN_DASHBOARD', '管理端仪表盘', 'ADMIN_DASHBOARD', '#2080f0', 'SYS', '100054',
        'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100066', 'BANNER_TYPE_POPUP', '弹窗', 'POPUP', '#d03050', 'SYS', '100061', 'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100073', 'BANNER_POSITION_WORKPLACE_TOP', '工作台顶部', 'WORKPLACE_TOP', '#722ed1', 'SYS', '100068', 'ENABLED',
        5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100126', 'NOTIFICATION_SEVERITY_URGENT', '紧急', 'URGENT', '#d03050', 'SYS', '100095', 'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100215', 'NOTIFICATION_CATEGORY_BIZ', '业务', 'BIZ', '#f0a020', 'SYS', '100210', 'ENABLED', 5);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100023', 'RESOURCE_TYPE_API_GROUP', '接口组', 'API_GROUP', '#1677ff', 'SYS', '100017', 'ENABLED', 6);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100060', 'BANNER_CATEGORY_SYSTEM_UPGRADE', '系统升级', 'SYSTEM_UPGRADE', '#d03050', 'SYS', '100054', 'ENABLED',
        6);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100067', 'BANNER_TYPE_SIDEBAR', '侧边栏', 'SIDEBAR', '#2080f0', 'SYS', '100061', 'ENABLED', 6);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100074', 'BANNER_POSITION_NOTICE_AREA', '公告区域', 'NOTICE_AREA', '#f0a020', 'SYS', '100068', 'ENABLED', 6);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100075', 'BANNER_POSITION_ADMIN_TOP', '管理端顶部', 'ADMIN_TOP', '#2080f0', 'SYS', '100068', 'ENABLED', 7);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100076', 'BANNER_POSITION_ADMIN_SIDEBAR', '管理端侧边栏', 'ADMIN_SIDEBAR', '#2080f0', 'SYS', '100068',
        'ENABLED', 8);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100007', 'ACCOUNT_TYPE', '账号类型', 'ACCOUNT_TYPE', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100008', 'ACCOUNT_TYPE_ADMIN', '管理端', 'ADMIN', '#722ed1', 'SYS', '100007', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100009', 'ACCOUNT_TYPE_PORTAL', '门户端', 'PORTAL', '#18a058', 'SYS', '100007', 'ENABLED', 2);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100220', 'NOTICE_KIND', '消息类型', 'NOTICE_KIND', '#2080f0', 'SYS', NULL, 'ENABLED', 0);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100221', 'NOTICE_KIND_NOTIFICATION', '通知', 'NOTIFICATION', '#2080f0', 'SYS', '100220', 'ENABLED', 1);
INSERT INTO sys_dict (id, code, label, value, color, category, parent_id, status, sort)
VALUES ('100222', 'NOTICE_KIND_ANNOUNCEMENT', '公告', 'ANNOUNCEMENT', '#18a058', 'SYS', '100220', 'ENABLED', 2);

-- sys_config: 145 rows
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_local_01', 'STORAGE_LOCAL_LOCAL_ROOT', './storage', 'STORAGE', 'LINUX 本地存储根目录', 10, 'STRING',
        'LINUX 本地存储根目录', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_local_03', 'STORAGE_LOCAL_PUBLIC_PATH', '/api/v1/files', 'STORAGE', '本地公开访问路径', 12, 'STRING',
        '本地公开访问路径', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_local_04', 'STORAGE_LOCAL_BASE_URL', '', 'STORAGE', '本地自定义基础 URL', 13, 'STRING',
        '本地自定义基础 URL', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_01', 'STORAGE_UPLOAD_MAX_BYTES', '10485760', 'UPLOAD', '上传文件大小上限（字节）', 1, 'INT',
        '上传文件大小上限（字节）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_local_02', 'STORAGE_LOCAL_WINDOWS_ROOT', 'D:/defaultUploadFolder', 'STORAGE', 'WINDOWS 本地存储根目录',
        11, 'STRING', 'WINDOWS 本地存储根目录', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sys_01', 'DEFAULT_FILE_ENGINE', 'LOCAL', 'STORAGE', '默认文件引擎', 1, 'STRING', '默认文件引擎', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_03', 'STORAGE_PRESIGN_EXPIRE_SECONDS', '3600', 'UPLOAD', '预签名 URL 有效期（秒）', 3, 'INT',
        '预签名 URL 有效期（秒）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_04', 'STORAGE_UPLOAD_ALLOWED_CONTENT_TYPES',
        '["image/jpeg","image/png","image/webp","application/pdf","text/plain","application/octet-stream"]', 'UPLOAD',
        '允许的 MIME 类型列表（JSON 数组）', 4, 'JSON', '允许的 MIME 类型列表（JSON 数组）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_05', 'STORAGE_UPLOAD_ALLOWED_EXTENSIONS',
        '[".jpg",".jpeg",".png",".webp",".pdf",".txt",".ini"]', 'UPLOAD', '允许的文件扩展名列表（JSON 数组）', 5, 'JSON',
        '允许的文件扩展名列表（JSON 数组）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_06', 'STORAGE_UPLOAD_DENIED_EXTENSIONS',
        '[".exe",".bat",".cmd",".sh",".js",".html",".php",".py",".jar"]', 'UPLOAD', '禁止上传的扩展名列表（JSON 数组）',
        6, 'JSON', '禁止上传的扩展名列表（JSON 数组）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_upload_07', 'STORAGE_UPLOAD_CATEGORY_MAX_LENGTH', '64', 'UPLOAD', '上传分类名最大长度', 7, 'INT',
        '上传分类名最大长度', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_df34085b029aedc6', 'AUTH_TOKEN_TTL_SECONDS', '2592000', 'AUTH_TOKEN', 'Token 过期时间（秒），默认 30 天', 1,
        'INT', 'Token 过期时间（秒），默认 30 天', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_9f4ba41bf4cec420', 'AUTH_PASSWORD_RESET_TOKEN_TTL_SECONDS', '600', 'AUTH_TOKEN',
        '密码重置 Token 有效期（秒）', 2, 'INT', '密码重置 Token 有效期（秒）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_e292016eaa176cd4', 'AUTH_LOGIN_FAILURE_WINDOW_SECONDS', '900', 'AUTH_LOGIN', '登录失败统计窗口（秒）', 1,
        'INT', '登录失败统计窗口（秒）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_59196f3cfe0c791e', 'AUTH_LOGIN_ACCOUNT_MAX_FAILURES', '5', 'AUTH_LOGIN', '单账号最大登录失败次数', 2,
        'INT', '单账号最大登录失败次数', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_397798808bce4338', 'AUTH_LOGIN_IP_MAX_FAILURES', '30', 'AUTH_LOGIN', '单 IP 最大登录失败次数', 3, 'INT',
        '单 IP 最大登录失败次数', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_68e8e38dfe6653f4', 'AUTH_LOGIN_LOCK_SECONDS', '900', 'AUTH_LOGIN', '登录锁定时间（秒）', 4, 'INT',
        '登录锁定时间（秒）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_9288450cadd9127c', 'AUTH_LOGIN_ADMIN_FAILURE_WINDOW_SECONDS', '300', 'AUTH_LOGIN',
        'ADMIN 登录失败窗口（秒）', 10, 'INT', 'ADMIN 登录失败窗口（秒）', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_521ad6a05b3b4ee2', 'AUTH_LOGIN_ADMIN_MAX_FAILURES', '5', 'AUTH_LOGIN', 'ADMIN 最大失败次数', 11, 'INT',
        'ADMIN 最大失败次数', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_6346132727a3f118', 'AUTH_LOGIN_ADMIN_LOCK_SECONDS', '300', 'AUTH_LOGIN', 'ADMIN 锁定时间（秒）', 12, 'INT',
        'ADMIN 锁定时间（秒）', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_3cf4009b79c4cdc0', 'AUTH_LOGIN_ADMIN_ALLOW_PHONE', 'TRUE', 'AUTH_LOGIN', 'ADMIN 允许手机号登录', 13,
        'BOOL', 'ADMIN 允许手机号登录', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_9801c7391e5a784b', 'AUTH_LOGIN_ADMIN_PHONE_NO_USER_POLICY', 'DENY', 'AUTH_LOGIN', 'ADMIN 手机号无用户策略',
        14, 'STRING', 'ADMIN 手机号无用户策略', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_85da42c429c11c0d', 'AUTH_LOGIN_ADMIN_ALLOW_EMAIL', 'TRUE', 'AUTH_LOGIN', 'ADMIN 允许邮箱登录', 15, 'BOOL',
        'ADMIN 允许邮箱登录', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_df6a5b9741f63718', 'AUTH_LOGIN_ADMIN_EMAIL_NO_USER_POLICY', 'DENY', 'AUTH_LOGIN', 'ADMIN 邮箱无用户策略',
        16, 'STRING', 'ADMIN 邮箱无用户策略', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_bd4d8532dd9d2c7b', 'AUTH_LOGIN_ADMIN_ALLOW_OTP', 'TRUE', 'AUTH_LOGIN', 'ADMIN 允许 OTP 登录', 17, 'BOOL',
        'ADMIN 允许 OTP 登录', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_a3edc3fb337f0fd5', 'AUTH_LOGIN_PORTAL_FAILURE_WINDOW_SECONDS', '300', 'AUTH_LOGIN',
        'PORTAL 登录失败窗口（秒）', 18, 'INT', 'PORTAL 登录失败窗口（秒）', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_412ec5ff8977bee1', 'AUTH_LOGIN_PORTAL_MAX_FAILURES', '5', 'AUTH_LOGIN', 'PORTAL 最大失败次数', 19, 'INT',
        'PORTAL 最大失败次数', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_c035540f2840759f', 'AUTH_LOGIN_PORTAL_LOCK_SECONDS', '300', 'AUTH_LOGIN', 'PORTAL 锁定时间（秒）', 20, 'INT',
        'PORTAL 锁定时间（秒）', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_73263fd26598e245', 'AUTH_LOGIN_PORTAL_ALLOW_PHONE', 'TRUE', 'AUTH_LOGIN', 'PORTAL 允许手机号登录', 21,
        'BOOL', 'PORTAL 允许手机号登录', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_8c93f75be4a015eb', 'AUTH_LOGIN_PORTAL_PHONE_NO_USER_POLICY', 'DENY', 'AUTH_LOGIN',
        'PORTAL 手机号无用户策略', 22, 'STRING', 'PORTAL 手机号无用户策略', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_845bdae0cbb2866c', 'AUTH_LOGIN_PORTAL_ALLOW_EMAIL', 'TRUE', 'AUTH_LOGIN', 'PORTAL 允许邮箱登录', 23,
        'BOOL', 'PORTAL 允许邮箱登录', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_e4a6b916ed9e923f', 'AUTH_LOGIN_PORTAL_EMAIL_NO_USER_POLICY', 'DENY', 'AUTH_LOGIN', 'PORTAL 邮箱无用户策略',
        24, 'STRING', 'PORTAL 邮箱无用户策略', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_b48d4cced07926b7', 'AUTH_LOGIN_PORTAL_ALLOW_OTP', 'TRUE', 'AUTH_LOGIN', 'PORTAL 允许 OTP 登录', 25, 'BOOL',
        'PORTAL 允许 OTP 登录', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_7c7536455a677234', 'AUTH_REGISTER_ADMIN_ENABLED', 'FALSE', 'AUTH_REGISTER', 'ADMIN 开放注册', 1, 'BOOL',
        'ADMIN 开放注册', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_4c6acf8e5e9275d9', 'AUTH_REGISTER_ADMIN_REQUIRE_PHONE', 'FALSE', 'AUTH_REGISTER', 'ADMIN 注册要求手机号',
        2, 'BOOL', 'ADMIN 注册要求手机号', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_6706c37cfca883e5', 'AUTH_REGISTER_ADMIN_REQUIRE_EMAIL', 'FALSE', 'AUTH_REGISTER', 'ADMIN 注册要求邮箱', 3,
        'BOOL', 'ADMIN 注册要求邮箱', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_276a9cb536b95fbc', 'AUTH_REGISTER_ADMIN_DEFAULT_ROLE_ID', '', 'AUTH_REGISTER', 'ADMIN 注册默认角色', 4,
        'STRING', 'ADMIN 注册默认角色', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_2a5bdbb1576b09a6', 'AUTH_REGISTER_ADMIN_DEFAULT_DEPT_ID', '', 'AUTH_REGISTER', 'ADMIN 注册默认部门', 5,
        'STRING', 'ADMIN 注册默认部门', 'ADMIN', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_a319c9d83ece6ada', 'AUTH_REGISTER_PORTAL_ENABLED', 'TRUE', 'AUTH_REGISTER', 'PORTAL 开放注册', 6, 'BOOL',
        'PORTAL 开放注册', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_7135b647c2b3c035', 'AUTH_REGISTER_PORTAL_REQUIRE_PHONE', 'FALSE', 'AUTH_REGISTER', 'PORTAL 注册要求手机号',
        7, 'BOOL', 'PORTAL 注册要求手机号', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_3ec435822ae2d002', 'AUTH_REGISTER_PORTAL_REQUIRE_EMAIL', 'TRUE', 'AUTH_REGISTER', 'PORTAL 注册要求邮箱', 8,
        'BOOL', 'PORTAL 注册要求邮箱', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_df87e86056b8b640', 'AUTH_REGISTER_PORTAL_DEFAULT_ROLE_ID', '', 'AUTH_REGISTER', 'PORTAL 注册默认角色', 9,
        'STRING', 'PORTAL 注册默认角色', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1b209b614b7b162c', 'AUTH_REGISTER_PORTAL_DEFAULT_DEPT_ID', '', 'AUTH_REGISTER', 'PORTAL 注册默认部门', 10,
        'STRING', 'PORTAL 注册默认部门', 'PORTAL', NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_dcf0f111fb004344', 'AUTH_DEFAULT_PASSWORD', '', 'AUTH_PASSWORD', '新建账户默认密码', 1, 'STRING',
        '新建账户默认密码', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_9c870cffa8867b07', 'PASSWORD_CHANGE_VERIFY_METHOD', 'OLD_PASSWORD', 'AUTH_PASSWORD', '自助改密验证方式', 2,
        'STRING', '自助改密验证方式', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_21da174072a0f0bb', 'PASSWORD_MIN_LENGTH', '8', 'AUTH_PASSWORD', '密码最小长度', 10, 'INT', '密码最小长度',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_2e41eb9efc88c078', 'PASSWORD_MAX_LENGTH', '128', 'AUTH_PASSWORD', '密码最大长度', 11, 'INT',
        '密码最大长度', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_88c6b448c4e8042e', 'PASSWORD_COMPLEXITY', 'DIGITS_UPPER_LOWER_SPECIAL', 'AUTH_PASSWORD', '密码复杂度', 12,
        'STRING', '密码复杂度', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_363c1bd765b8f5be', 'PASSWORD_MAX_CONSECUTIVE_CHARS', '3', 'AUTH_PASSWORD', '最大连续相同字符数', 13, 'INT',
        '最大连续相同字符数', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_95047010c8cffed4', 'PASSWORD_FORBID_USER_INFO', 'TRUE', 'AUTH_PASSWORD', '禁止包含用户信息', 14, 'BOOL',
        '禁止包含用户信息', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_cf505abfcea42e3c', 'PASSWORD_FORBID_HISTORICAL', 'TRUE', 'AUTH_PASSWORD', '禁止复用历史密码', 15, 'BOOL',
        '禁止复用历史密码', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1e8e0e5c42c8f7ab', 'PASSWORD_HISTORY_CHECK_COUNT', '5', 'AUTH_PASSWORD', '历史密码检查条数', 16, 'INT',
        '历史密码检查条数', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_77632725699872aa', 'PASSWORD_FORBID_WEAK_LIST', 'TRUE', 'AUTH_PASSWORD', '禁止弱密码库命中', 17, 'BOOL',
        '禁止弱密码库命中', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_5fb99add24efb532', 'PASSWORD_VALIDITY_DAYS', '90', 'AUTH_PASSWORD', '密码有效期（天）', 18, 'INT',
        '密码有效期（天）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_d759b943eb3eba43', 'PASSWORD_EXPIRY_WARNING_DAYS', '7', 'AUTH_PASSWORD', '密码过期提前提醒（天）', 19, 'INT',
        '密码过期提前提醒（天）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1da3474838da8c34', 'PASSWORD_CUSTOM_WEAK_WORDS', '', 'AUTH_PASSWORD', '自定义弱密码词（逗号分隔）', 20,
        'STRING', '自定义弱密码词（逗号分隔）', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_58414339bc280bd6', 'DEFAULT_EMAIL_ENGINE', 'LOCAL', 'MAIL', '默认邮件引擎', 1, 'STRING', '默认邮件引擎',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_2acb1f201faa512d', 'MAIL_LOCAL_HOST', 'localhost', 'MAIL', 'SMTP 服务器地址', 10, 'STRING',
        'SMTP 服务器地址', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_84f70e8cc74e7e9b', 'MAIL_LOCAL_PORT', '1025', 'MAIL', 'SMTP 端口', 11, 'INT', 'SMTP 端口', NULL, NULL,
        TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_813571013d71784d', 'MAIL_LOCAL_USERNAME', '', 'MAIL', 'SMTP 用户名', 12, 'STRING', 'SMTP 用户名', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_717b2404165e2b0a', 'MAIL_LOCAL_PASSWORD', '', 'MAIL', 'SMTP 密码', 13, 'STRING', 'SMTP 密码', NULL, NULL,
        TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_6cff7e453fe2304e', 'MAIL_LOCAL_FROM_EMAIL', 'test@hei-fastapi.local', 'MAIL', '发件人邮箱', 14, 'STRING',
        '发件人邮箱', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_e099681b11c0ee4f', 'MAIL_LOCAL_FROM_NAME', 'hei-fastapi', 'MAIL', '发件人显示名称', 15, 'STRING',
        '发件人显示名称', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_b2da3fee87e0dd1f', 'MAIL_LOCAL_AUTH_REQUIRED', 'FALSE', 'MAIL', 'SMTP 是否需要认证', 16, 'BOOL',
        'SMTP 是否需要认证', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_802f8c2f3efd8536', 'MAIL_LOCAL_USE_SSL', 'FALSE', 'MAIL', 'SMTP 使用 SSL', 17, 'BOOL', 'SMTP 使用 SSL',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_27be176dc9266dcc', 'MAIL_LOCAL_USE_STARTTLS', 'FALSE', 'MAIL', 'SMTP 使用 STARTTLS', 18, 'BOOL',
        'SMTP 使用 STARTTLS', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_b97b4627d571e9e9', 'MAIL_ALIYUN_ACCESS_KEY_ID', '', 'MAIL', '阿里云邮件 AccessKeyId', 20, 'STRING',
        '阿里云邮件 AccessKeyId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_dd0fc126a107b79e', 'MAIL_ALIYUN_ACCESS_KEY_SECRET', '', 'MAIL', '阿里云邮件 AccessKeySecret', 21, 'STRING',
        '阿里云邮件 AccessKeySecret', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_972fbb9f8c160446', 'MAIL_ALIYUN_ACCOUNT_NAME', '', 'MAIL', '阿里云发信地址', 22, 'STRING',
        '阿里云发信地址', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_75f3eafaf65e3cab', 'MAIL_TENCENT_SECRET_ID', '', 'MAIL', '腾讯云邮件 SecretId', 30, 'STRING',
        '腾讯云邮件 SecretId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_474cf1635e2d65d9', 'MAIL_TENCENT_SECRET_KEY', '', 'MAIL', '腾讯云邮件 SecretKey', 31, 'STRING',
        '腾讯云邮件 SecretKey', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_eb6ff89b0555300f', 'MAIL_TENCENT_FROM_EMAIL', '', 'MAIL', '腾讯云发件邮箱', 32, 'STRING', '腾讯云发件邮箱',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_84d3de8cd967733a', 'DEFAULT_SMS_ENGINE', 'ALIYUN', 'SMS', '默认短信引擎', 1, 'STRING', '默认短信引擎',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_4b0b045351490c90', 'SMS_ALIYUN_ACCESS_KEY_ID', '', 'SMS', '阿里云短信 AccessKeyId', 10, 'STRING',
        '阿里云短信 AccessKeyId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_e2bc3adc53a793ff', 'SMS_ALIYUN_ACCESS_KEY_SECRET', '', 'SMS', '阿里云短信 AccessKeySecret', 11, 'STRING',
        '阿里云短信 AccessKeySecret', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_99dabb0bc6e4a331', 'SMS_ALIYUN_SIGN_NAME', '', 'SMS', '阿里云短信签名', 12, 'STRING', '阿里云短信签名',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_b36d23e6047971bb', 'SMS_TENCENT_SECRET_ID', '', 'SMS', '腾讯云短信 SecretId', 20, 'STRING',
        '腾讯云短信 SecretId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_6028a8f49ab077ba', 'SMS_TENCENT_SECRET_KEY', '', 'SMS', '腾讯云短信 SecretKey', 21, 'STRING',
        '腾讯云短信 SecretKey', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_ec651dd2afd644bc', 'SMS_TENCENT_SDK_APP_ID', '', 'SMS', '腾讯云短信 SdkAppId', 22, 'STRING',
        '腾讯云短信 SdkAppId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_e8fb8e521a365dc2', 'SMS_TENCENT_SIGN_NAME', '', 'SMS', '腾讯云短信签名', 23, 'STRING', '腾讯云短信签名',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_57e28945f48834a9', 'DEFAULT_MESSAGE_PUSH_ENGINE', 'DINGTALK', 'PUSH', '默认消息推送引擎', 1, 'STRING',
        '默认消息推送引擎', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_89b4b2fd016928e6', 'PUSH_DINGTALK_WEBHOOK', '', 'PUSH', '钉钉 Webhook', 10, 'STRING', '钉钉 Webhook', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_6d571cb0b08380d9', 'PUSH_DINGTALK_SECRET', '', 'PUSH', '钉钉加签密钥', 11, 'STRING', '钉钉加签密钥', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_879c74e0d724800e', 'PUSH_LARK_WEBHOOK', '', 'PUSH', '飞书 Webhook', 20, 'STRING', '飞书 Webhook', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1d568c38d7ec2f23', 'PUSH_LARK_SECRET', '', 'PUSH', '飞书加签密钥', 21, 'STRING', '飞书加签密钥', NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_0680575e52e15d07', 'PUSH_WECHAT_WORK_WEBHOOK', '', 'PUSH', '企业微信 Webhook', 30, 'STRING',
        '企业微信 Webhook', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_a2be24a3a6abeff0', 'AUDIT_ALERT_ENABLED', 'TRUE', 'AUDIT_ALERT', '审计告警总开关', 1, 'BOOL',
        '审计告警总开关', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_2299a1e718af6f56', 'AUDIT_ALERT_NOTIFY_EMAIL', 'TRUE', 'AUDIT_ALERT', '邮件通知', 2, 'BOOL', '邮件通知',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_bdd06a9ecd9561a0', 'AUDIT_ALERT_NOTIFY_PUSH', 'TRUE', 'AUDIT_ALERT', '推送通知', 3, 'BOOL', '推送通知',
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_5ab4365d79759a36', 'AUDIT_ALERT_NOTIFY_CUSTOM_WEBHOOK', 'FALSE', 'AUDIT_ALERT', '自定义 Webhook 通知', 4,
        'BOOL', '自定义 Webhook 通知', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1fb16a022bd1ee13', 'AUDIT_ALERT_WEBHOOK_URL', '', 'AUDIT_ALERT', 'Webhook 地址', 5, 'STRING',
        'Webhook 地址', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_295649e7a593148c', 'AUDIT_ALERT_WEBHOOK_SECRET', '', 'AUDIT_ALERT', 'Webhook 签名密钥', 6, 'STRING',
        'Webhook 签名密钥', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_2370fd3a6f2f8c68', 'AUDIT_ALERT_ANALYSIS_INTERVAL_SECONDS', '60', 'AUDIT_ALERT', '分析周期(秒)', 7, 'INT',
        '分析周期(秒)', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_3091f8900b127ff5', 'AUDIT_ALERT_ALERT_COOLDOWN_SECONDS', '1800', 'AUDIT_ALERT', '告警冷却(秒)', 8, 'INT',
        '告警冷却(秒)', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_f7f86f743c41c302', 'AUDIT_ALERT_RULE_BRUTE_FORCE', 'TRUE', 'AUDIT_ALERT', '暴力破解检测', 10, 'BOOL',
        '暴力破解检测', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_88059105f3bfd3bd', 'AUDIT_ALERT_RULE_UNUSUAL_HOURS', 'TRUE', 'AUDIT_ALERT', '异常时间操作检测', 11, 'BOOL',
        '异常时间操作检测', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_8394da8a8cca0551', 'AUDIT_ALERT_RULE_SENSITIVE_OPS', 'TRUE', 'AUDIT_ALERT', '敏感操作监控', 12, 'BOOL',
        '敏感操作监控', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_1be5b5a733cdb21b', 'AUDIT_ALERT_RULE_BULK_DELETE', 'TRUE', 'AUDIT_ALERT', '批量删除检测', 13, 'BOOL',
        '批量删除检测', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_452a32888fa5d2f8', 'AUDIT_ALERT_RULE_IP_ANOMALY', 'TRUE', 'AUDIT_ALERT', 'IP 异常检测', 14, 'BOOL',
        'IP 异常检测', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_ad5e5c81c4766435', 'AUDIT_ALERT_BRUTE_FORCE_THRESHOLD', '10', 'AUDIT_ALERT', '暴力破解阈值', 20, 'INT',
        '暴力破解阈值', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_a4e7a42d3948bf0b', 'AUDIT_ALERT_BULK_DELETE_THRESHOLD', '20', 'AUDIT_ALERT', '批量删除阈值', 21, 'INT',
        '批量删除阈值', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_28c8a4a855e9fad1', 'AUDIT_ALERT_IP_ANOMALY_THRESHOLD', '3', 'AUDIT_ALERT', 'IP异常阈值', 22, 'INT',
        'IP异常阈值', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_0540565620e0467f', 'COPYRIGHT_TEXT', 'hei-fastapi', 'SYS', '版权文案', 1, 'STRING', '版权文案', NULL, NULL,
        TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_b43e7757a174ca18', 'COPYRIGHT_URL', '', 'SYS', '版权链接', 2, 'STRING', '版权链接', NULL, NULL, TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_c00733c0055182cb', 'MAIL_TEMPLATE_RESET_PASSWORD_CODE',
        '{"subject": "{{app_name}} 密码重置", "body": "请点击以下链接重置密码，该链接将在 {{expire_minutes}} 分钟内有效。\n\n{{reset_link}}"}',
        'MAIL_TEMPLATE', '重置密码邮件模板', 1, 'JSON', '重置密码邮件模板', NULL, 'RESET_PASSWORD_CODE', TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_d61b9f0c91d4d7d0', 'MAIL_TEMPLATE_LOGIN_CODE',
        '{"subject": "{{app_name}} 登录验证码", "body": "您的登录验证码是 {{code}}，{{expire_minutes}} 分钟内有效。"}',
        'MAIL_TEMPLATE', '登录验证码邮件模板', 2, 'JSON', '登录验证码邮件模板', NULL, 'LOGIN_CODE', TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_ce9bc36c9f61f79f', 'SMS_TEMPLATE_LOGIN_CODE', '{"code": "", "content": "登录验证码 {{code}}"}',
        'SMS_TEMPLATE', '登录验证码短信模板', 1, 'JSON', '登录验证码短信模板', NULL, 'LOGIN_CODE', TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_f7804b0c3e90d4d0', 'MAIL_TEMPLATE_CHANGE_PASSWORD_CODE',
        '{"subject": "{{app_name}} 修改密码验证码", "body": "验证码 {{code}}，{{expire_minutes}} 分钟内有效。"}',
        'MAIL_TEMPLATE', '修改密码邮件模板', 3, 'JSON', '修改密码邮件模板', NULL, 'CHANGE_PASSWORD_CODE', TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_a488dc18ffd25b5d', 'MAIL_TEMPLATE_REGISTER_SUCCESS',
        '{"subject": "欢迎注册 {{app_name}}", "body": "账号 {{account}} 注册成功。"}', 'MAIL_TEMPLATE',
        '注册成功邮件模板', 4, 'JSON', '注册成功邮件模板', NULL, 'REGISTER_SUCCESS', TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_9db853784cc408aa', 'SMS_TEMPLATE_CHANGE_PASSWORD_CODE', '{"code": "", "content": "改密验证码 {{code}}"}',
        'SMS_TEMPLATE', '修改密码短信模板', 2, 'JSON', '修改密码短信模板', NULL, 'CHANGE_PASSWORD_CODE', TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125220999168', 'STORAGE_ALIYUN_ENDPOINT', 'oss-cn-hangzhou.aliyuncs.com', 'STORAGE', NULL, 0, 'STRING',
        NULL, NULL, NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125220999169', 'STORAGE_ALIYUN_BUCKET', 'defaultbucket', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL,
        FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125220999170', 'STORAGE_ALIYUN_REGION', 'cn-hangzhou', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL,
        FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125220999171', 'STORAGE_ALIYUN_USE_SSL', 'TRUE', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193472', 'STORAGE_ALIYUN_BASE_URL', '', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193473', 'STORAGE_ALIYUN_PUBLIC_PATH', '/api/v1/files', 'STORAGE', NULL, 0, 'STRING', NULL, NULL,
        NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_aliyun_ak_01', 'STORAGE_ALIYUN_ACCESS_KEY', '', 'STORAGE', '阿里云 OSS AccessKey', 0, 'STRING',
        '阿里云 OSS AccessKey', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_aliyun_sk_01', 'STORAGE_ALIYUN_SECRET_KEY', '', 'STORAGE', '阿里云 OSS SecretKey', 0, 'STRING',
        '阿里云 OSS SecretKey', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193474', 'STORAGE_TENCENT_ENDPOINT', '', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193475', 'STORAGE_TENCENT_BUCKET', 'defaultbucket', 'STORAGE', NULL, 0, 'STRING', NULL, NULL,
        NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193476', 'STORAGE_TENCENT_REGION', 'ap-beijing', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL,
        FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193477', 'STORAGE_TENCENT_USE_SSL', 'TRUE', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193478', 'STORAGE_TENCENT_BASE_URL', '', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193479', 'STORAGE_TENCENT_PUBLIC_PATH', '/api/v1/files', 'STORAGE', NULL, 0, 'STRING', NULL, NULL,
        NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_tencent_ak_01', 'STORAGE_TENCENT_ACCESS_KEY', '', 'STORAGE', '腾讯云 COS SecretId', 0, 'STRING',
        '腾讯云 COS SecretId', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_tencent_sk_01', 'STORAGE_TENCENT_SECRET_KEY', '', 'STORAGE', '腾讯云 COS SecretKey', 0, 'STRING',
        '腾讯云 COS SecretKey', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193483', 'STORAGE_MINIO_BUCKET', 'vms', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193484', 'STORAGE_MINIO_REGION', '', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193485', 'STORAGE_MINIO_USE_SSL', 'FALSE', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193486', 'STORAGE_MINIO_BASE_URL', '', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL, FALSE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193487', 'STORAGE_MINIO_PUBLIC_PATH', '/api/v1/files', 'STORAGE', NULL, 0, 'STRING', NULL, NULL,
        NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193482', 'STORAGE_MINIO_ENDPOINT', 'http://127.0.0.1:9000', 'STORAGE', NULL, 0, 'STRING', NULL,
        NULL, NULL, FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_3c98bcca99e448cd', 'AUTH_PASSWORD_RESET_URL_ADMIN', 'http://localhost:5173/auth/forgot-password',
        'AUTH_TOKEN', 'ADMIN 密码重置页完整 URL', 3, 'STRING', 'ADMIN 密码重置页完整 URL', 'ADMIN', NULL, TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_cd29b96922a8b478', 'AUTH_PASSWORD_RESET_URL_PORTAL', 'http://localhost:5174/auth/forgot-password',
        'AUTH_TOKEN', 'PORTAL 密码重置页完整 URL', 4, 'STRING', 'PORTAL 密码重置页完整 URL', 'PORTAL', NULL, TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_01', 'STORAGE_RUSTFS_BUCKET', 'defaultbucket', 'STORAGE', 'RustFS 存储桶', 40, 'STRING', NULL,
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_05', 'STORAGE_RUSTFS_REGION', 'us-east-1', 'STORAGE', 'RustFS Region', 44, 'STRING', NULL, NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_06', 'STORAGE_RUSTFS_USE_SSL', 'FALSE', 'STORAGE', 'RustFS 是否 SSL', 45, 'BOOL', NULL, NULL,
        NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_07', 'STORAGE_RUSTFS_BASE_URL', '', 'STORAGE', 'RustFS 自定义基础 URL', 46, 'STRING', NULL,
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_08', 'STORAGE_RUSTFS_PUBLIC_PATH', '/api/v1/files', 'STORAGE', 'RustFS 公开访问路径', 47,
        'STRING', NULL, NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_02', 'STORAGE_RUSTFS_ENDPOINT', 'http://127.0.0.1:9002', 'STORAGE', 'RustFS S3 API 端点', 41,
        'STRING', NULL, NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193480', 'STORAGE_MINIO_ACCESS_KEY', 'admin', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL,
        FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('7491869125225193481', 'STORAGE_MINIO_SECRET_KEY', '123456789', 'STORAGE', NULL, 0, 'STRING', NULL, NULL, NULL,
        FALSE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_03', 'STORAGE_RUSTFS_ACCESS_KEY', 'admin', 'STORAGE', 'RustFS Access Key', 42, 'STRING', NULL,
        NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sto_rustfs_04', 'STORAGE_RUSTFS_SECRET_KEY', '123456789', 'STORAGE', 'RustFS Secret Key', 43, 'STRING',
        NULL, NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_acct_cancel_ret_01', 'ACCOUNT_CANCEL_RETENTION_DAYS', '15', 'OTHER', '注销账号保留天数', 10, 'INT',
        '注销账号保留天数', NULL, NULL, TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_mail_acct_cancel_01', 'MAIL_TEMPLATE_ACCOUNT_CANCELLED',
        '{"subject": "{{app_name}} 账号注销确认", "body": "您好，您的账号已申请注销。\n\n我们将在 {{retention_days}} 天内保留账号数据；到期且期间未再登录使用后，系统将彻底删除账号及相关数据。\n\n预计清理时间：{{purge_at}}\n如非本人操作，请尽快联系管理员。"}',
        'MAIL_TEMPLATE', '账号注销确认邮件模板', 20, 'JSON', '账号注销确认邮件模板', NULL, 'ACCOUNT_CANCELLED', TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_mail_acct_purge_01', 'MAIL_TEMPLATE_ACCOUNT_PURGED',
        '{"subject": "{{app_name}} 账号已彻底删除", "body": "您好，您此前注销的账号已完成保留期清理，账号及相关个人数据已彻底删除。\n\n清理时间：{{purged_at}}\n感谢您曾使用 {{app_name}}。"}',
        'MAIL_TEMPLATE', '账号彻底删除邮件模板', 21, 'JSON', '账号彻底删除邮件模板', NULL, 'ACCOUNT_PURGED', TRUE,
        '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sms_acct_cancel_01', 'SMS_TEMPLATE_ACCOUNT_CANCELLED',
        '{"code": "", "content": "账号已申请注销，将于{{retention_days}}天后彻底删除。"}', 'SMS_TEMPLATE',
        '账号注销确认短信模板', 20, 'JSON', '账号注销确认短信模板', NULL, 'ACCOUNT_CANCELLED', TRUE, '{}'::json);
INSERT INTO sys_config (id, config_key, config_value, category, remark, sort_code, value_type, label, scope, scene,
                        is_builtin, ext_json)
VALUES ('cfg_sms_acct_purge_01', 'SMS_TEMPLATE_ACCOUNT_PURGED',
        '{"code": "", "content": "您的账号已完成注销清理并彻底删除。"}', 'SMS_TEMPLATE', '账号彻底删除短信模板', 21,
        'JSON', '账号彻底删除短信模板', NULL, 'ACCOUNT_PURGED', TRUE, '{}'::json);

-- sys_role: 1 rows
INSERT INTO sys_role (id, code, name, category, scope_type, owner_dept_id, sort, status, is_builtin, description, extra)
VALUES ('1', 'SUPER_ADMIN', '超级管理员', 'SYS', 'PLATFORM', NULL, 1, 'ENABLED', TRUE, '系统内置超级管理员角色',
        '{}'::json);

-- sys_account: 1 rows
INSERT INTO sys_account (id, password_hash, account_type, account_status)
VALUES ('1', '$2a$10$4MMY6TXxGspLyqDu2YH9h.FzysyWzzvykTsNvvssIeAu0vc3g0eh6', 'ADMIN', 'ENABLED');

-- sys_account_identity: 1 rows
INSERT INTO sys_account_identity (id, account_id, identity_type, identifier, verified, is_primary, bind_status)
VALUES ('1', '1', 'ACCOUNT', 'superadmin', TRUE, TRUE, 'BOUND');

-- admin_user_profile: 1 rows
INSERT INTO admin_user_profile (account_id, name, nickname, avatar, signature, phone, email, remark)
VALUES ('1', '超级管理员', '超管', '2026/08/08/7939e8a27f78435a994ff132e8e63e0f.png', NULL, NULL, NULL,
        '系统内置超管账户');

-- sys_iam_relation: 35 rows
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202204', 'RESOURCE', '202204', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:update', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '编辑消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202205', 'RESOURCE', '202205', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:delete', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '删除消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202209', 'RESOURCE', '202209', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:publish', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '发布消息', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202240', 'RESOURCE', '202240', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:revoke', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '撤回消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202241', 'RESOURCE', '202241', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:pin', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '置顶消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203011', 'RESOURCE', '203011', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestactivity:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 10, 'ENABLED', '分页活动', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203012', 'RESOURCE', '203012', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestactivity:create', 'CASCADE', 'ALL', '[]'::json, FALSE, 20, 'ENABLED', '新增活动', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203013', 'RESOURCE', '203013', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestactivity:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 30, 'ENABLED', '详情活动', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('1', 'ACCOUNT', '1', 'ADMIN', 'ACCOUNT_ROLE', 'ROLE', '1', '', 'CASCADE', 'SELF', '[]'::json, FALSE, 99,
        'ENABLED', NULL, NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203014', 'RESOURCE', '203014', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestactivity:update', 'CASCADE', 'ALL', '[]'::json, FALSE, 40, 'ENABLED', '编辑活动', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203015', 'RESOURCE', '203015', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestactivity:delete', 'CASCADE', 'ALL', '[]'::json, FALSE, 50, 'ENABLED', '删除活动', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203021', 'RESOURCE', '203021', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 10, 'ENABLED', '分页目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203022', 'RESOURCE', '203022', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:create', 'CASCADE', 'ALL', '[]'::json, FALSE, 20, 'ENABLED', '新增目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203023', 'RESOURCE', '203023', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 30, 'ENABLED', '详情目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203024', 'RESOURCE', '203024', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:update', 'CASCADE', 'ALL', '[]'::json, FALSE, 40, 'ENABLED', '编辑目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203025', 'RESOURCE', '203025', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:delete', 'CASCADE', 'ALL', '[]'::json, FALSE, 50, 'ENABLED', '删除目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203026', 'RESOURCE', '203026', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestcatalog:list', 'CASCADE', 'ALL', '[]'::json, FALSE, 90, 'ENABLED', '树列表目录', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_audit_menu_login_log', 'RESOURCE', '200028', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'sys:audit:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', NULL, NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_audit_btn_login_log_detail', 'RESOURCE', '201060', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'sys:audit:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', NULL, NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_audit_menu_audit', 'RESOURCE', '200029', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'sys:audit:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', NULL, NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_audit_btn_audit_detail', 'RESOURCE', '201061', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'sys:audit:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', NULL, NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202201', 'RESOURCE', '202201', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '分页消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202202', 'RESOURCE', '202202', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:create', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '新增消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_notice_202203', 'RESOURCE', '202203', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'message:notice:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 0, 'ENABLED', '详情消息', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203031', 'RESOURCE', '203031', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestorder:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 10, 'ENABLED', '分页订单', NULL, NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203032', 'RESOURCE', '203032', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestorder:create', 'CASCADE', 'ALL', '[]'::json, FALSE, 20, 'ENABLED', '新增订单', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203033', 'RESOURCE', '203033', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestorder:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 30, 'ENABLED', '详情订单', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203034', 'RESOURCE', '203034', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestorder:update', 'CASCADE', 'ALL', '[]'::json, FALSE, 40, 'ENABLED', '编辑订单', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203035', 'RESOURCE', '203035', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestorder:delete', 'CASCADE', 'ALL', '[]'::json, FALSE, 50, 'ENABLED', '删除订单', NULL, NULL,
        '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203041', 'RESOURCE', '203041', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:page', 'CASCADE', 'ALL', '[]'::json, FALSE, 10, 'ENABLED', '分页知识分类', NULL,
        NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203042', 'RESOURCE', '203042', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:create', 'CASCADE', 'ALL', '[]'::json, FALSE, 20, 'ENABLED', '新增知识分类', NULL,
        NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203043', 'RESOURCE', '203043', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:detail', 'CASCADE', 'ALL', '[]'::json, FALSE, 30, 'ENABLED', '详情知识分类', NULL,
        NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203044', 'RESOURCE', '203044', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:update', 'CASCADE', 'ALL', '[]'::json, FALSE, 40, 'ENABLED', '编辑知识分类', NULL,
        NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203045', 'RESOURCE', '203045', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:delete', 'CASCADE', 'ALL', '[]'::json, FALSE, 50, 'ENABLED', '删除知识分类', NULL,
        NULL, '{}'::json);
INSERT INTO sys_iam_relation (id, subject_type, subject_id, account_type, relation_type, target_type, target_id,
                              target_key, grant_mode, data_scope, custom_scope_dept_ids, is_primary, sort, status,
                              description, reason, expired_at, extra)
VALUES ('rel_cgtest_203046', 'RESOURCE', '203046', 'ADMIN', 'RESOURCE_PERMISSION', 'PERMISSION', '',
        'biz:cgtestknowledgecategory:list', 'CASCADE', 'ALL', '[]'::json, FALSE, 90, 'ENABLED', '树列表知识分类', NULL,
        NULL, '{}'::json);

-- sys_weak_password: 5 rows
INSERT INTO sys_weak_password (id, password)
VALUES ('wp_01', '123456');
INSERT INTO sys_weak_password (id, password)
VALUES ('wp_02', 'password');
INSERT INTO sys_weak_password (id, password)
VALUES ('wp_03', 'admin123');
INSERT INTO sys_weak_password (id, password)
VALUES ('wp_04', 'qwerty');
INSERT INTO sys_weak_password (id, password)
VALUES ('wp_05', '111111');
