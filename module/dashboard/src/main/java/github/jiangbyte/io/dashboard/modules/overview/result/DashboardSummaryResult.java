package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘顶部汇总：账号总数、在线会话、文件数与存储占用。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResult {
    private long accountTotal;
    private long onlineSessions;
    private long fileTotal;
    private long storageBytes;
}
