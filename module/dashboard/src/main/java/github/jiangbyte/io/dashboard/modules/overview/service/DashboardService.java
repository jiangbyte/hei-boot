package github.jiangbyte.io.dashboard.modules.overview.service;

import github.jiangbyte.io.dashboard.modules.overview.result.DashboardOverviewResult;

/**
 * 仪表盘领域服务：组装管理端总览统计数据。
 *
 * Author: Charlie
 */
public interface DashboardService {

    /**
     * 组装管理端仪表盘总览数据（汇总、账号、IAM、运维、趋势、文件）。
     */
    DashboardOverviewResult overview();

}
