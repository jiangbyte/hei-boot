-- 管理端通知/公告测试数据（面向 ADMIN）
-- 刷新页面或重新登录后即可验证：弹窗 / 工作台公告 / 铃铛通知中心

DELETE FROM sys_notice_read WHERE notice_id LIKE '8801%';
DELETE FROM sys_notice WHERE id LIKE '8801%';

-- 1) 仅弹窗：登录后应主动弹出
INSERT INTO sys_notice (
  id, kind, title, content, content_type, category, severity, target_scope,
  target_account_types, target_account_ids, target_dept_ids, target_role_ids,
  publish_locations, is_pinned, pinned_until, sender_account_type, sender_account_id,
  source_type, source_id, status, publish_at, revoked_at, expire_at, view_count, extra,
  created_at, created_by, updated_at, updated_by
) VALUES (
  '8801000000000000001',
  'ANNOUNCEMENT',
  '【弹窗】平台版本更新说明',
  $html$
<div style="margin-bottom:12px;padding:10px 12px;background:#f6ffed;border:1px solid #b7eb8f;border-radius:4px;">
  HEI Admin 测试环境已更新，请阅读下列说明。
</div>
<p>感谢各位同事关注，欢迎体验最新管理端能力。</p>
<table border="1" cellpadding="8" cellspacing="0" style="width:100%;border-collapse:collapse;margin:12px 0;">
  <thead>
    <tr style="background:#f5f7fb;">
      <th>序号</th><th>名称</th><th>简介</th><th>说明</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>工作台</td><td>个人常用应用与日志</td><td>仅本人数据</td></tr>
    <tr><td>2</td><td>消息中心</td><td>通知与公告</td><td>支持已读标记</td></tr>
    <tr><td>3</td><td>内容运营</td><td>轮播与消息管理</td><td>管理端配置</td></tr>
  </tbody>
</table>
<p>如有问题请前往「个人中心 → 我的消息」查看历史记录。</p>
$html$,
  'html',
  'SYSTEM',
  'INFO',
  'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{"popup": true}'::json,
  false, NULL, 'SYSTEM', NULL, NULL, NULL,
  'PUBLISHED', NOW() - INTERVAL '10 minutes', NULL, NOW() + INTERVAL '30 days', 0, '{}'::json,
  NOW(), '1', NOW(), '1'
);

-- 2) 仅工作台公告区：侧栏列表可点，不主动弹
INSERT INTO sys_notice (
  id, kind, title, content, content_type, category, severity, target_scope,
  target_account_types, target_account_ids, target_dept_ids, target_role_ids,
  publish_locations, is_pinned, pinned_until, sender_account_type, sender_account_id,
  source_type, source_id, status, publish_at, revoked_at, expire_at, view_count, extra,
  created_at, created_by, updated_at, updated_by
) VALUES (
  '8801000000000000002',
  'ANNOUNCEMENT',
  '【工作台】本周运营安排',
  '本周重点：核对菜单授权、补充常用应用、关注登录异常。本条仅展示在工作台公告区，不会主动弹窗。',
  'text',
  'SYSTEM',
  'SUCCESS',
  'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{"workspace": true, "center": true}'::json,
  true, NOW() + INTERVAL '7 days', 'SYSTEM', NULL, NULL, NULL,
  'PUBLISHED', NOW() - INTERVAL '1 hour', NULL, NOW() + INTERVAL '60 days', 0, '{}'::json,
  NOW(), '1', NOW(), '1'
);

-- 3) 工作台 + 通知中心（markdown）
INSERT INTO sys_notice (
  id, kind, title, content, content_type, category, severity, target_scope,
  target_account_types, target_account_ids, target_dept_ids, target_role_ids,
  publish_locations, is_pinned, pinned_until, sender_account_type, sender_account_id,
  source_type, source_id, status, publish_at, revoked_at, expire_at, view_count, extra,
  created_at, created_by, updated_at, updated_by
) VALUES (
  '8801000000000000003',
  'ANNOUNCEMENT',
  '【工作台】安全基线自查清单',
  E'## 请重点检查\n\n1. 管理员密码强度与 MFA\n2. 高危权限是否按最小授权\n3. 审计日志是否可正常查询\n\n> 本条用于验证 Markdown 渲染与工作台点击详情。',
  'markdown',
  'SYSTEM',
  'WARNING',
  'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{"workspace": true, "center": true}'::json,
  false, NULL, 'SYSTEM', NULL, NULL, NULL,
  'PUBLISHED', NOW() - INTERVAL '2 hours', NULL, NOW() + INTERVAL '45 days', 0, '{}'::json,
  NOW(), '1', NOW(), '1'
);

-- 4) 第二条弹窗（测队列：关闭第一条后应弹出下一条）
INSERT INTO sys_notice (
  id, kind, title, content, content_type, category, severity, target_scope,
  target_account_types, target_account_ids, target_dept_ids, target_role_ids,
  publish_locations, is_pinned, pinned_until, sender_account_type, sender_account_id,
  source_type, source_id, status, publish_at, revoked_at, expire_at, view_count, extra,
  created_at, created_by, updated_at, updated_by
) VALUES (
  '8801000000000000004',
  'ANNOUNCEMENT',
  '【弹窗】第二条测试弹窗',
  '如果你先关掉了上一条弹窗，应该会看到本条。点「不再提示」可永久关闭；点「稍后提醒」仅本会话跳过。',
  'text',
  'SYSTEM',
  'WARNING',
  'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{"popup": true}'::json,
  false, NULL, 'SYSTEM', NULL, NULL, NULL,
  'PUBLISHED', NOW() - INTERVAL '5 minutes', NULL, NOW() + INTERVAL '30 days', 0, '{}'::json,
  NOW(), '1', NOW(), '1'
);

-- 5~8) 普通通知：铃铛 / 我的消息
INSERT INTO sys_notice (
  id, kind, title, content, content_type, category, severity, target_scope,
  target_account_types, target_account_ids, target_dept_ids, target_role_ids,
  publish_locations, is_pinned, pinned_until, sender_account_type, sender_account_id,
  source_type, source_id, status, publish_at, revoked_at, expire_at, view_count, extra,
  created_at, created_by, updated_at, updated_by
) VALUES
(
  '8801000000000000005',
  'NOTIFICATION',
  '角色权限已变更提醒',
  '你的角色权限可能已由管理员调整，请重新打开相关页面验证菜单与按钮是否可见。',
  'text', 'IAM', 'WARNING', 'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{}'::json, false, NULL, 'SYSTEM', NULL, 'SYSTEM', NULL,
  'PUBLISHED', NOW() - INTERVAL '30 minutes', NULL, NULL, 0, '{}'::json,
  NOW(), '1', NOW(), '1'
),
(
  '8801000000000000006',
  'NOTIFICATION',
  '导出任务已完成',
  '审计日志导出任务已完成，可在「文件管理」中下载对应结果文件。',
  'text', 'SYSTEM', 'SUCCESS', 'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{}'::json, false, NULL, 'SYSTEM', NULL, 'SYSTEM', NULL,
  'PUBLISHED', NOW() - INTERVAL '45 minutes', NULL, NULL, 0, '{}'::json,
  NOW(), '1', NOW(), '1'
),
(
  '8801000000000000007',
  'NOTIFICATION',
  '异常登录尝试告警',
  '检测到短时间内多次失败登录尝试（测试数据）。若非本人操作，请立即修改密码并检查会话。',
  'text', 'SECURITY', 'ERROR', 'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{}'::json, false, NULL, 'SYSTEM', NULL, 'SYSTEM', NULL,
  'PUBLISHED', NOW() - INTERVAL '20 minutes', NULL, NULL, 0, '{}'::json,
  NOW(), '1', NOW(), '1'
),
(
  '8801000000000000008',
  'NOTIFICATION',
  '新同事账号已开通',
  '测试账号已开通并可登录管理端。请在组织架构中核对部门归属与角色绑定。',
  'text', 'SYSTEM', 'INFO', 'ACCOUNT_TYPE',
  '["ADMIN"]'::json, '[]'::json, '[]'::json, '[]'::json,
  '{}'::json, false, NULL, 'SYSTEM', NULL, 'SYSTEM', NULL,
  'PUBLISHED', NOW() - INTERVAL '15 minutes', NULL, NULL, 0, '{}'::json,
  NOW(), '1', NOW(), '1'
);

-- 清理旧弹窗公告的已读，方便再次测试主动弹出（仅维护预告那条若被标已读）
DELETE FROM sys_notice_read
WHERE notice_id IN ('8801000000000000001', '8801000000000000004')
  AND account_type = 'ADMIN';

SELECT id, kind, title, publish_locations::text AS locations, severity, publish_at
FROM sys_notice
WHERE id LIKE '8801%'
ORDER BY publish_at DESC;
