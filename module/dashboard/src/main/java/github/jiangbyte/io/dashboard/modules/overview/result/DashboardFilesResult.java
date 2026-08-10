package github.jiangbyte.io.dashboard.modules.overview.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 仪表盘文件统计结果：按内容类型的数量分布。
 *
 * Author: Charlie
 */
@Data
public class DashboardFilesResult {
    private List<DashboardStatusItemResult> byContentType = new ArrayList<>();
}
