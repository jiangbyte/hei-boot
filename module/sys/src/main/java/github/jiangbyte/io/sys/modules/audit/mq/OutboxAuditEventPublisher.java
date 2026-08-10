package github.jiangbyte.io.sys.modules.audit.mq;

import github.jiangbyte.io.common.mq.HeiMqConstants;
import github.jiangbyte.io.common.mq.audit.AuditEventMessage;
import github.jiangbyte.io.common.mq.audit.AuditEventPublisher;
import github.jiangbyte.io.sys.modules.audit.service.AuditOutboxService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 基于 Outbox 的审计事件发布器。
 *
 * Author: Charlie
 */
@Component
@Primary
@RequiredArgsConstructor
public class OutboxAuditEventPublisher implements AuditEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(OutboxAuditEventPublisher.class);

    private final AuditOutboxService auditOutboxService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(AuditEventMessage message) {
        if (message == null) {
            return;
        }
        String outboxId = auditOutboxService.enqueue(message);
        message.setOutboxId(outboxId);
        try {
            rabbitTemplate.convertAndSend(
                    HeiMqConstants.AUDIT_EXCHANGE,
                    HeiMqConstants.AUDIT_ROUTING_KEY,
                    message
            );
        } catch (RuntimeException ex) {
            // outbox 保持 PENDING 供回收；不使业务请求失败。
            log.warn("Failed to publish audit event to MQ; outboxId={} remains PENDING", outboxId, ex);
        }
    }
}
