package github.jiangbyte.io.dashboard.modules.overview.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import github.jiangbyte.io.common.satoken.StpKit;

import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.dashboard.modules.overview.result.DashboardOverviewResult;
import github.jiangbyte.io.dashboard.modules.overview.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * 管理端仪表盘 API：聚合账号、IAM、运维与文件等总览指标。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService dashboardService;

    /** 获取管理端仪表盘总览统计。 */
    @GetMapping("/v1/admin/dashboard/overview")
    @SaCheckPermission(value = "dashboard:overview:view", type = StpKit.TYPE_ADMIN)
    public ApiResponse<DashboardOverviewResult> overview() {
        return ApiResponse.ok(dashboardService.overview());
    }
}
