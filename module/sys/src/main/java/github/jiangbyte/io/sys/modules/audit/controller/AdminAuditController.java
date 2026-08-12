package github.jiangbyte.io.sys.modules.audit.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditLog;
import github.jiangbyte.io.sys.modules.audit.param.SysAuditPageParam;
import github.jiangbyte.io.sys.modules.audit.service.AuditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端操作审计 API：分页查询审计日志。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminAuditController {

    private final AuditService auditService;

    /** 分页查询。 */
    @GetMapping("/v1/admin/sys/audit/page")
    @SaCheckPermission(value = "sys:audit:page", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<SysOperationAuditLog>> page(@Valid @ModelAttribute SysAuditPageParam param) {
        return ApiResponse.ok(auditService.page(param));
    }

    /** 查询详情。 */
    @GetMapping("/v1/admin/sys/audit/detail")
    @SaCheckPermission(value = "sys:audit:detail", type = StpKit.TYPE_ADMIN)
    public ApiResponse<SysOperationAuditLog> detail(@RequestParam String id) {
        return ApiResponse.ok(auditService.detail(id));
    }
}
