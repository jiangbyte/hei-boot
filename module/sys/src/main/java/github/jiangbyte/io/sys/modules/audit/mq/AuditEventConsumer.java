package github.jiangbyte.io.sys.modules.audit.mq;

import github.jiangbyte.io.common.mq.HeiMqConstants;
import github.jiangbyte.io.common.mq.audit.AuditEventMessage;
import github.jiangbyte.io.sys.modules.audit.service.AuditOutboxService;
import github.jiangbyte.io.sys.modules.audit.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 审计事件 MQ 消费者：落库与告警联动。
 *
 * Author: Charlie
 */
@Component
@ConditionalOnProperty(prefix = "hei.mq.audit", name = "consume-enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class AuditEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);

    private final AuditService auditService;
    private final AuditOutboxService auditOutboxService;

    @RabbitListener(queues = HeiMqConstants.AUDIT_QUEUE)
    public void onMessage(AuditEventMessage message) {
        try {
            if (message != null && StringUtils.hasText(message.getOutboxId())
                    && !auditOutboxService.claim(message.getOutboxId())) {
                // 已 claim/ack（回收重复）— 跳过以免重复插入。
                return;
            }
            auditService.persistEvent(message);
            if (message != null && StringUtils.hasText(message.getOutboxId())) {
                auditOutboxService.acknowledge(message.getOutboxId());
            }
        } catch (Exception exception) {
            log.error("Failed to persist audit event", exception);
            throw exception;
        }
    }
}
