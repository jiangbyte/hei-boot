package github.jiangbyte.io.sys.modules.audit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.mq.audit.AuditEventMessage;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditLog;
import github.jiangbyte.io.sys.modules.audit.mapper.SysOperationAuditLogMapper;
import github.jiangbyte.io.sys.modules.audit.param.SysAuditPageParam;
import github.jiangbyte.io.sys.modules.audit.service.AuditService;
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
@Service
public class AuditServiceImpl extends ServiceImpl<SysOperationAuditLogMapper, SysOperationAuditLog> implements AuditService {

    @Override
    @ReadDataSource
    public Page<SysOperationAuditLog> page(SysAuditPageParam param) {
        // 分页查询
        return this.getBaseMapper().selectPage(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<SysOperationAuditLog>lambdaQuery()
                        .eq(StringUtils.hasText(param.getModule()), SysOperationAuditLog::getModule, param.getModule())
                        .eq(StringUtils.hasText(param.getAction()), SysOperationAuditLog::getAction, param.getAction())
                        .eq(StringUtils.hasText(param.getAccountId()), SysOperationAuditLog::getAccountId, param.getAccountId())
                        .eq(param.getSuccess() != null, SysOperationAuditLog::getSuccess, param.getSuccess())
                        .orderByDesc(SysOperationAuditLog::getCreatedAt));
    }

    @Override
    @ReadDataSource
    public SysOperationAuditLog detail(String id) {
        // 按主键加载
        SysOperationAuditLog log = this.getById(id);
        if (log == null) {
            // 不存在则抛出业务异常
            throw new BizException(404, "Audit log not found");
        }
        return log;
    }

    @Override
    @Transactional
    public void persistEvent(AuditEventMessage event) {
        if (event == null) {
            return;
        }
        // 映射事件字段并落库
        SysOperationAuditLog log = new SysOperationAuditLog();
        log.setModule(buildModule(event.getResourceType()));
        log.setResourceType(event.getResourceType());
        log.setAction(event.getAction());
        log.setSummary((event.getMethod() == null ? "" : event.getMethod()) + " "
                + (event.getPath() == null ? "" : event.getPath()));
        boolean success = event.getStatusCode() == null || event.getStatusCode() < 400;
        log.setSuccess(success);
        log.setErrorMessage(success || event.getStatusCode() == null ? null : String.valueOf(event.getStatusCode()));
        log.setAccountId(event.getAccountId());
        log.setAccountType(event.getAccountType());
        log.setRequestId(event.getRequestId());
        log.setIp(event.getIp());
        log.setUserAgent(event.getUserAgent());
        log.setCreatedAt(event.getOccurredAt() == null
                ? OffsetDateTime.now()
                : OffsetDateTime.ofInstant(event.getOccurredAt(), ZoneOffset.UTC));
        this.save(log);
    }

    private static String buildModule(String resourceType) {
        return "resources".equals(resourceType) ? "resource" : "iam";
    }
}
