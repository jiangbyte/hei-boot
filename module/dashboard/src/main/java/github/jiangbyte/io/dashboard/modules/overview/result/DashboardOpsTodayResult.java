package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘当日运维统计结果：审计总量/失败数与待处理反馈数。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOpsTodayResult {
    private long auditTotal;
    private long auditFailed;
    private long feedbackPending;
}
