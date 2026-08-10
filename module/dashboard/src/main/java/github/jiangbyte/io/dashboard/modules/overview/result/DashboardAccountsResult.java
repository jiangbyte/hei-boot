package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 仪表盘账号统计结果：启用/禁用/今日新增及按类型分布。
 *
 * Author: Charlie
 */
@Data
public class DashboardAccountsResult {
    private long enabled;
    private long disabled;
    private long todayNew;
    private List<DashboardStatusItemResult> byType = new ArrayList<>();
}
