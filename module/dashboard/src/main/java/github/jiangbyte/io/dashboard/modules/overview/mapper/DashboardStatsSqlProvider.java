package github.jiangbyte.io.dashboard.modules.overview.mapper;

import github.jiangbyte.io.common.mybatis.dialect.DbDialectHolder;

import java.util.Map;

/**
 * 仪表盘按日统计 SQL Provider。
 *
 * Author: Charlie
 */
public final class DashboardStatsSqlProvider {

    private DashboardStatsSqlProvider() {
    }

    public static String accountDailyCounts(Map<String, Object> params) {
        return dailyCounts("sys_account", "created_at");
    }

    public static String auditDailyCounts(Map<String, Object> params) {
        return dailyCounts("sys_operation_audit_log", "created_at");
    }

    private static String dailyCounts(String table, String column) {
        var dialect = DbDialectHolder.get();
        return """
                SELECT %s, COUNT(*) AS cnt
                FROM %s
                WHERE %s >= #{since}
                GROUP BY %s
                ORDER BY day
                """.formatted(
                dialect.dailyBucketSelect(column),
                table,
                column,
                dialect.dailyBucketGroupBy(column));
    }
}
