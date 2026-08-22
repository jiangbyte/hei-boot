package github.jiangbyte.io.profile.modules.identity.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.profile.modules.identity.param.IdentityPageParam;
import github.jiangbyte.io.profile.modules.identity.param.IdentityRevokeParam;
import github.jiangbyte.io.profile.modules.identity.param.RealNameCaseApproveParam;
import github.jiangbyte.io.profile.modules.identity.param.RealNameCaseRejectParam;
import github.jiangbyte.io.profile.modules.identity.param.RealNameCaseReviewPageParam;
import github.jiangbyte.io.profile.modules.identity.result.IdentityPageResult;
import github.jiangbyte.io.profile.modules.identity.result.RealNameCaseDetailResult;
import github.jiangbyte.io.profile.modules.identity.result.RealNameCaseSummaryResult;
import github.jiangbyte.io.profile.modules.identity.service.ProfileIdentityService;
import github.jiangbyte.io.profile.modules.identity.service.RealNameCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端实名认证审核与快照管理 API。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminRealNameManageController {

    private final RealNameCaseService realNameCaseService;
    private final ProfileIdentityService profileIdentityService;

    @GetMapping("/v1/admin/sys/real-name-case/review-page")
    @SaCheckPermission(value = "sys:realname:review:verify", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<RealNameCaseSummaryResult>> reviewPage(@Valid @ModelAttribute RealNameCaseReviewPageParam param) {
        return ApiResponse.ok(realNameCaseService.reviewPage(param));
    }

    @GetMapping("/v1/admin/sys/real-name-case/detail")
    @SaCheckPermission(value = "sys:realname:review:verify", type = StpKit.TYPE_ADMIN)
    public ApiResponse<RealNameCaseDetailResult> detail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(realNameCaseService.detail(param.getId()));
    }

    @PostMapping("/v1/admin/sys/real-name-case/approve")
    @SaCheckPermission(value = "sys:realname:review:verify", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "real_name_case", action = "approve")
    public ApiResponse<Void> approve(@Valid @RequestBody RealNameCaseApproveParam param) {
        realNameCaseService.approve(param);
        return ApiResponse.ok();
    }

    @PostMapping("/v1/admin/sys/real-name-case/reject")
    @SaCheckPermission(value = "sys:realname:review:verify", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "real_name_case", action = "reject")
    public ApiResponse<Void> reject(@Valid @RequestBody RealNameCaseRejectParam param) {
        realNameCaseService.reject(param);
        return ApiResponse.ok();
    }

    @GetMapping("/v1/admin/sys/identity/page")
    @SaCheckPermission(value = "sys:realname:identity:revoke", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<IdentityPageResult>> identityPage(@Valid @ModelAttribute IdentityPageParam param) {
        return ApiResponse.ok(profileIdentityService.page(param));
    }

    @PostMapping("/v1/admin/sys/identity/revoke")
    @SaCheckPermission(value = "sys:realname:identity:revoke", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "profile_identity", action = "revoke")
    public ApiResponse<Void> revoke(@Valid @RequestBody IdentityRevokeParam param) {
        profileIdentityService.revoke(param, LoginHelper.requireUser().getAccountId());
        return ApiResponse.ok();
    }
}
