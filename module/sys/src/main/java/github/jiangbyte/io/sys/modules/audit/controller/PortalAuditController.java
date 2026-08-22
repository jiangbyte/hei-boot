package github.jiangbyte.io.sys.modules.audit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
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
 * 门户端操作审计 API：当前用户本人日志查询。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortalAuditController {

    private final AuditService auditService;

    /** 当前门户用户本人审计日志分页。 */
    @GetMapping("/v1/portal/sys/audit/my-page")
    public ApiResponse<Page<SysOperationAuditLog>> myPage(@Valid @ModelAttribute SysAuditPageParam param) {
        return ApiResponse.ok(auditService.myPage(param));
    }

    /** 当前门户用户本人审计详情。 */
    @GetMapping("/v1/portal/sys/audit/my-detail")
    public ApiResponse<SysOperationAuditLog> myDetail(@RequestParam String id) {
        return ApiResponse.ok(auditService.myDetail(id));
    }
}
