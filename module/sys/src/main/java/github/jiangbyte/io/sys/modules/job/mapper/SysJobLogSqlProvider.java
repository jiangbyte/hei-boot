package github.jiangbyte.io.sys.modules.job.mapper;

import github.jiangbyte.io.common.mybatis.dialect.DbDialectHolder;

import java.util.Map;

/**
 * 任务日志清理 SQL Provider。
 *
 * Author: Charlie
 */
public final class SysJobLogSqlProvider {

    private SysJobLogSqlProvider() {
    }

    public static String deleteExpired(Map<String, Object> params) {
        return DbDialectHolder.get().deleteExpiredJobLogSql();
    }
}
