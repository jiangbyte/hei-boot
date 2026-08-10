package github.jiangbyte.io.sys.modules.audit.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import github.jiangbyte.io.common.mq.HeiMqConstants;
import github.jiangbyte.io.common.mq.audit.AuditEventMessage;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditOutbox;
import github.jiangbyte.io.sys.modules.audit.mapper.SysOperationAuditOutboxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * 审计 Outbox 回收定时任务：重试未投递事件。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class AuditOutboxReclaimJob {

    private final SysOperationAuditOutboxMapper outboxMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @XxlJob("auditOutboxReclaimJob")
    public void execute() {
        // 组装查询条件
        List<SysOperationAuditOutbox> pending = outboxMapper.selectList(Wrappers.<SysOperationAuditOutbox>lambdaQuery()
                .eq(SysOperationAuditOutbox::getStatus, "PENDING")
                .orderByAsc(SysOperationAuditOutbox::getCreatedAt)
                .last("limit 50"));
        int republished = 0;
        for (SysOperationAuditOutbox row : pending) {
            try {
                AuditEventMessage message = objectMapper.readValue(row.getPayload(), AuditEventMessage.class);
                message.setOutboxId(row.getId());
                rabbitTemplate.convertAndSend(
                        HeiMqConstants.AUDIT_EXCHANGE,
                        HeiMqConstants.AUDIT_ROUTING_KEY,
                        message
                );
                republished++;
            } catch (Exception ex) {
                XxlJobHelper.log("Failed to reclaim audit outbox id=" + row.getId() + ": " + ex.getMessage());
            }
        }
        XxlJobHelper.log("Audit outbox reclaim republished=" + republished + " of " + pending.size());
    }
}
