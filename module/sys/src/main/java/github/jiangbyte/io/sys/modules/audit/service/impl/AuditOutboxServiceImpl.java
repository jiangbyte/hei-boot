package github.jiangbyte.io.sys.modules.audit.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import github.jiangbyte.io.common.mq.audit.AuditEventMessage;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditOutbox;
import github.jiangbyte.io.sys.modules.audit.mapper.SysOperationAuditOutboxMapper;
import github.jiangbyte.io.sys.modules.audit.service.AuditOutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;

/**
 * 审计 Outbox 服务实现：消息投递与失败回收。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AuditOutboxServiceImpl implements AuditOutboxService {

    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_CLAIMED = "CLAIMED";

    private final SysOperationAuditOutboxMapper outboxMapper;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String enqueue(AuditEventMessage event) {
        // 序列化事件并写入待投递 Outbox
        SysOperationAuditOutbox row = new SysOperationAuditOutbox();
        row.setPayload(objectMapper.writeValueAsString(event));
        row.setStatus(STATUS_PENDING);
        row.setAttempts(0);
        row.setCreatedAt(OffsetDateTime.now());
        outboxMapper.insert(row);
        return row.getId();
    }

    @Override
    @Transactional
    public boolean claim(String outboxId) {
        if (!StringUtils.hasText(outboxId)) {
            return false;
        }
        // 仅认领 PENDING 记录并增加 attempts
        int updated = outboxMapper.update(null, Wrappers.<SysOperationAuditOutbox>lambdaUpdate()
                .eq(SysOperationAuditOutbox::getId, outboxId)
                .eq(SysOperationAuditOutbox::getStatus, STATUS_PENDING)
                .set(SysOperationAuditOutbox::getStatus, STATUS_CLAIMED)
                .setSql("attempts = attempts + 1")
                .set(SysOperationAuditOutbox::getClaimedAt, OffsetDateTime.now()));
        return updated > 0;
    }

    @Override
    @Transactional
    public void acknowledge(String outboxId) {
        if (!StringUtils.hasText(outboxId)) {
            return;
        }
        // 投递成功后删除 Outbox 行
        outboxMapper.deleteById(outboxId);
    }
}
