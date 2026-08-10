package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘趋势点：日期、指标类型与计数值。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTrendPointResult {
    private String date;
    private String type;
    private long value;
}
