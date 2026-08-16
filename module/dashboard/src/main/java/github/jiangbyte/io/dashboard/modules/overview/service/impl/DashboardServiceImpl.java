package github.jiangbyte.io.dashboard.modules.overview.service.impl;

import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.dashboard.modules.overview.mapper.DashboardStatsMapper;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardAccountsResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardFilesResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardIamResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardOpsTodayResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardOverviewResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardStatusItemResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardSummaryResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardTrendPointResult;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardTrendsResult;
import github.jiangbyte.io.dashboard.modules.overview.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link github.jiangbyte.io.dashboard.modules.overview.service.DashboardService} 实现：聚合 SQL 统计、在线会话估算与近七日趋势补齐。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private static final DateTimeFormatter DAY = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final ZoneId BIZ_ZONE = ZoneId.of("Asia/Shanghai");

    private final DashboardStatsMapper statsMapper;

    @ReadDataSource
    @Override
    public DashboardOverviewResult overview() {
        // 计算今日起点与近七日窗口（业务时区 Asia/Shanghai）
        OffsetDateTime now = OffsetDateTime.now(BIZ_ZONE);
        OffsetDateTime dayStart = now.toLocalDate().atStartOfDay(BIZ_ZONE).toOffsetDateTime();
        OffsetDateTime since = dayStart.minusDays(6);

        // 一次聚合计数
        Map<String, Object> counts = statsMapper.aggregateCounts(dayStart);

        // 组装汇总（含在线会话估算）
        DashboardOverviewResult overview = new DashboardOverviewResult();
        overview.setSummary(new DashboardSummaryResult(
                asLong(counts.get("account_total")),
                onlineSessionCount(),
                asLong(counts.get("file_total")),
                asLong(counts.get("storage_bytes"))));

        // 组装账号启用/禁用与类型分布
        DashboardAccountsResult accounts = new DashboardAccountsResult();
        accounts.setEnabled(asLong(counts.get("account_enabled")));
        accounts.setDisabled(asLong(counts.get("account_disabled")));
        accounts.setTodayNew(asLong(counts.get("account_today_new")));
        accounts.setByType(toStatusItems(statsMapper.accountByType()));
        overview.setAccounts(accounts);

        // 组装 IAM 资源计数
        overview.setIam(new DashboardIamResult(
                asLong(counts.get("role_count")),
                asLong(counts.get("dept_count")),
                asLong(counts.get("group_count")),
                asLong(counts.get("menu_count"))));

        // 组装今日运维指标
        overview.setOpsToday(new DashboardOpsTodayResult(
                asLong(counts.get("audit_total")),
                asLong(counts.get("audit_failed")),
                asLong(counts.get("feedback_pending"))));

        // 补齐近七日账号/审计趋势
        DashboardTrendsResult trends = new DashboardTrendsResult();
        trends.setAccountTrend(buildTrend(statsMapper.accountDailyCounts(since), since, "accounts"));
        trends.setAuditTrend(buildTrend(statsMapper.auditDailyCounts(since), since, "audits"));
        overview.setTrends(trends);

        // 组装文件类型分布后返回
        DashboardFilesResult files = new DashboardFilesResult();
        files.setByContentType(toStatusItems(statsMapper.fileTypeShare()));
        overview.setFiles(files);
        return overview;
    }

    private List<DashboardTrendPointResult> buildTrend(
            List<Map<String, Object>> rows, OffsetDateTime since, String type) {
        Map<String, Long> byDay = new HashMap<>();
        for (Map<String, Object> row : rows) {
            byDay.put(String.valueOf(row.get("day")), asLong(row.get("cnt")));
        }
        List<DashboardTrendPointResult> points = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            String day = since.plusDays(i).atZoneSameInstant(BIZ_ZONE).toLocalDate().format(DAY);
            points.add(new DashboardTrendPointResult(day.substring(5), type, byDay.getOrDefault(day, 0L)));
        }
        return points;
    }

    private List<DashboardStatusItemResult> toStatusItems(List<Map<String, Object>> rows) {
        return rows.stream()
                .map(row -> new DashboardStatusItemResult(
                        String.valueOf(row.get("name") == null ? "unknown" : row.get("name")),
                        asLong(row.get("value"))))
                .toList();
    }

    private long onlineSessionCount() {
        try {
            int admin = StpKit.ADMIN.searchSessionId("", 0, -1, false).size();
            int portal = StpKit.PORTAL.searchSessionId("", 0, -1, false).size();
            return admin + portal;
        } catch (Exception ignored) {
            return 0L;
        }
    }

    private static long asLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(value.toString());
    }
}
