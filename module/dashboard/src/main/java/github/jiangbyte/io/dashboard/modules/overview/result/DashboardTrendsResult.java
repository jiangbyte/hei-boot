package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 仪表盘趋势集合：账号新增趋势与审计趋势。
 *
 * Author: Charlie
 */
@Data
public class DashboardTrendsResult {
    private List<DashboardTrendPointResult> accountTrend = new ArrayList<>();
    private List<DashboardTrendPointResult> auditTrend = new ArrayList<>();
}
