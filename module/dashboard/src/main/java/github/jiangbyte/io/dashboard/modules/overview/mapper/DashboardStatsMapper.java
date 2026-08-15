package github.jiangbyte.io.dashboard.modules.overview.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘统计 SQL Mapper：聚合计数、账号分型、近七日趋势与文件类型占比查询。
 *
 * Author: Charlie
 */
@Mapper
public interface DashboardStatsMapper {

    /**
     * 聚合当日相关的账号/文件/IAM/审计/反馈等计数。
     */
    @Select("""
            SELECT
              (SELECT COUNT(*) FROM sys_account) AS account_total,
              (SELECT COUNT(*) FROM sys_account WHERE account_status = 'ENABLED') AS account_enabled,
              (SELECT COUNT(*) FROM sys_account WHERE account_status = 'DISABLED') AS account_disabled,
              (SELECT COUNT(*) FROM sys_account WHERE created_at >= #{dayStart}) AS account_today_new,
              (SELECT COUNT(*) FROM sys_file) AS file_total,
              (SELECT COALESCE(SUM(size), 0) FROM sys_file) AS storage_bytes,
              (SELECT COUNT(*) FROM sys_role) AS role_count,
              (SELECT COUNT(*) FROM sys_dept) AS dept_count,
              (SELECT COUNT(*) FROM sys_group) AS group_count,
              (SELECT COUNT(*) FROM sys_resource
                 WHERE resource_type = 'MENU' AND status = 'ENABLED') AS menu_count,
              (SELECT COUNT(*) FROM sys_operation_audit_log
                 WHERE created_at >= #{dayStart}) AS audit_total,
              (SELECT COUNT(*) FROM sys_operation_audit_log
                 WHERE created_at >= #{dayStart} AND success = false) AS audit_failed,
              (SELECT COUNT(*) FROM sys_feedback WHERE status = 'PENDING') AS feedback_pending
            """)
    Map<String, Object> aggregateCounts(@Param("dayStart") OffsetDateTime dayStart);

    /**
     * 按账户类型分组统计账号数量。
     */
    @Select("""
            SELECT account_type AS name, COUNT(*) AS value
            FROM sys_account
            GROUP BY account_type
            ORDER BY value DESC
            """)
    List<Map<String, Object>> accountByType();

    /**
     * 统计自 since 起每日新增账号数。
     */
    @Select("""
            SELECT TO_CHAR(created_at::date, 'YYYY-MM-DD') AS day, COUNT(*) AS cnt
            FROM sys_account
            WHERE created_at >= #{since}
            GROUP BY created_at::date
            ORDER BY day
            """)
    List<Map<String, Object>> accountDailyCounts(@Param("since") OffsetDateTime since);

    /**
     * 统计自 since 起每日审计日志数。
     */
    @Select("""
            SELECT TO_CHAR(created_at::date, 'YYYY-MM-DD') AS day, COUNT(*) AS cnt
            FROM sys_operation_audit_log
            WHERE created_at >= #{since}
            GROUP BY created_at::date
            ORDER BY day
            """)
    List<Map<String, Object>> auditDailyCounts(@Param("since") OffsetDateTime since);

    /**
     * 按内容类型统计文件数量（取前 8）。
     */
    @Select("""
            SELECT COALESCE(content_type, 'unknown') AS name, COUNT(*) AS value
            FROM sys_file
            GROUP BY content_type
            ORDER BY value DESC
            LIMIT 8
            """)
    List<Map<String, Object>> fileTypeShare();
}
