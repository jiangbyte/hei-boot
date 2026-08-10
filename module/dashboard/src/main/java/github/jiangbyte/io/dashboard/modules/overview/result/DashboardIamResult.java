package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 仪表盘 IAM 统计结果：角色、部门、用户组与菜单数量。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardIamResult {
    private long roleCount;
    private long deptCount;
    private long groupCount;
    private long menuCount;
}
