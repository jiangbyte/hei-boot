package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.Data;

/**
 * 仪表盘总览响应：汇总、账号、IAM、当日运维、趋势与文件分布。
 *
 * Author: Charlie
 */
@Data
public class DashboardOverviewResult {
    private DashboardSummaryResult summary;
    private DashboardAccountsResult accounts;
    private DashboardIamResult iam;
    private DashboardOpsTodayResult opsToday;
    private DashboardTrendsResult trends;
    private DashboardFilesResult files;
}
