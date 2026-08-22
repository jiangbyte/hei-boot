package github.jiangbyte.io.sys.modules.audit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.log.audit.AuditEventMessage;
import github.jiangbyte.io.common.log.audit.AuditLabelCatalog;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditLog;
import github.jiangbyte.io.sys.modules.audit.mapper.SysOperationAuditLogMapper;
import github.jiangbyte.io.sys.modules.audit.param.SysAuditPageParam;
import github.jiangbyte.io.sys.modules.audit.service.AuditService;
import github.jiangbyte.io.sys.modules.audit.support.AuditOperatorSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * 操作审计服务实现：日志查询与持久化。
 *
 * Author: Charlie
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl extends ServiceImpl<SysOperationAuditLogMapper, SysOperationAuditLog> implements AuditService {

    private final AuditOperatorSupport auditOperatorSupport;

    @Override
    @ReadDataSource
    public Page<SysOperationAuditLog> page(SysAuditPageParam param) {
        Page<SysOperationAuditLog> page = this.getBaseMapper().selectPage(
                new Page<>(param.getCurrent(), param.getSize()), buildQuery(param));
        auditOperatorSupport.enrichOperatorNames(page.getRecords());
        return page;
    }

    @Override
    @ReadDataSource
    public Page<SysOperationAuditLog> myPage(SysAuditPageParam param) {
        LoginUser user = LoginHelper.currentUser()
                .orElseThrow(() -> new BizException(401, "未登录"));
        SysAuditPageParam mine = param == null ? new SysAuditPageParam() : param;
        mine.setAccountId(user.getAccountId());
        return page(mine);
    }

    @Override
    @ReadDataSource
    public SysOperationAuditLog detail(String id) {
        SysOperationAuditLog log = this.getById(id);
        if (log == null) {
            throw new BizException(404, "Audit log not found");
        }
        auditOperatorSupport.enrichOperatorNames(java.util.List.of(log));
        return log;
    }

    @Override
    @ReadDataSource
    public SysOperationAuditLog myDetail(String id) {
        LoginUser user = LoginHelper.currentUser()
                .orElseThrow(() -> new BizException(401, "未登录"));
        SysOperationAuditLog log = detail(id);
        if (!user.getAccountId().equals(log.getAccountId())) {
            throw new BizException(404, "Audit log not found");
        }
        return log;
    }

    private com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SysOperationAuditLog> buildQuery(
            SysAuditPageParam param) {
        return Wrappers.<SysOperationAuditLog>lambdaQuery()
                .eq(StringUtils.hasText(param.getModule()), SysOperationAuditLog::getModule, param.getModule())
                .eq(StringUtils.hasText(param.getAction()), SysOperationAuditLog::getAction, param.getAction())
                .ne(!StringUtils.hasText(param.getAction()) && StringUtils.hasText(param.getExcludeAction()),
                        SysOperationAuditLog::getAction, param.getExcludeAction())
                .eq(StringUtils.hasText(param.getAccountId()), SysOperationAuditLog::getAccountId, param.getAccountId())
                .eq(param.getSuccess() != null, SysOperationAuditLog::getSuccess, param.getSuccess())
                .orderByDesc(SysOperationAuditLog::getCreatedAt);
    }

    @Override
    @Transactional
    public void persistEvent(AuditEventMessage event) {
        if (event == null) {
            return;
        }
        // request_id 非空时幂等：已存在则跳过（配合唯一索引；并发冲突吞掉 DuplicateKeyException）
        if (StringUtils.hasText(event.getRequestId())) {
            Long exists = this.getBaseMapper().selectCount(Wrappers.<SysOperationAuditLog>lambdaQuery()
                    .eq(SysOperationAuditLog::getRequestId, event.getRequestId()));
            if (exists != null && exists > 0) {
                log.debug("Skip duplicate audit event, requestId={}", event.getRequestId());
                return;
            }
        }
        // 映射事件字段并落库
        SysOperationAuditLog auditLog = new SysOperationAuditLog();
        auditLog.setModule(buildModule(event.getResourceType()));
        auditLog.setResourceType(event.getResourceType());
        auditLog.setResourceId(event.getResourceId());
        auditLog.setAction(event.getAction());
        String summary = event.getSummary();
        if (!StringUtils.hasText(summary)) {
            summary = AuditLabelCatalog.buildContent(
                    event.getAction(),
                    event.getResourceType(),
                    event.getActionName(),
                    event.getOperatorName(),
                    event.getStatusCode() == null || event.getStatusCode() < 400,
                    event.getBeforeData(),
                    event.getAfterData());
        }
        if (StringUtils.hasText(summary) && summary.length() > 2000) {
            summary = summary.substring(0, 1997) + "...";
        }
        auditLog.setSummary(summary);
        auditLog.setBeforeData(event.getBeforeData());
        auditLog.setAfterData(event.getAfterData());
        boolean success = event.getStatusCode() == null || event.getStatusCode() < 400;
        auditLog.setSuccess(success);
        auditLog.setErrorMessage(success || event.getStatusCode() == null ? null : String.valueOf(event.getStatusCode()));
        auditLog.setAccountId(event.getAccountId());
        auditLog.setAccountType(event.getAccountType());
        auditLog.setRequestId(event.getRequestId());
        auditLog.setIp(event.getIp());
        auditLog.setUserAgent(event.getUserAgent());
        auditLog.setOperatorName(auditOperatorSupport.snapshotOperatorName(event));
        auditLog.setActionName(StringUtils.hasText(event.getActionName())
                ? event.getActionName()
                : AuditLabelCatalog.actionName(event.getResourceType(), event.getAction(), null));
        auditLog.setActionType(StringUtils.hasText(event.getActionType())
                ? event.getActionType()
                : AuditLabelCatalog.actionType(event.getAction(), null));
        auditLog.setModuleLabel(StringUtils.hasText(event.getModuleLabel())
                ? event.getModuleLabel()
                : AuditLabelCatalog.moduleLabel(event.getResourceType()));
        auditLog.setDurationMs(event.getDurationMs());
        auditLog.setCreatedAt(event.getOccurredAt() == null
                ? OffsetDateTime.now()
                : OffsetDateTime.ofInstant(event.getOccurredAt(), ZoneOffset.UTC));
        try {
            this.save(auditLog);
        } catch (DataIntegrityViolationException ex) {
            // Unique index on request_id (scripts/db.sql) / concurrent insert races
            log.debug("Ignore duplicate audit insert, requestId={}", event.getRequestId());
        }
    }

    /**
     * 由 resourceType 推导 module：auth / sys_file → sys / iam_account → iam。
     */
    static String buildModule(String resourceType) {
        if (!StringUtils.hasText(resourceType)) {
            return "unknown";
        }
        String normalized = resourceType.trim();
        if ("resources".equals(normalized)) {
            return "resource";
        }
        int idx = normalized.indexOf('_');
        return idx > 0 ? normalized.substring(0, idx) : normalized;
    }
}
