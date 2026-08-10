package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘名称-数值项，用于分型/占比等柱状或列表展示。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatusItemResult {
    private String name;
    private long value;
}
