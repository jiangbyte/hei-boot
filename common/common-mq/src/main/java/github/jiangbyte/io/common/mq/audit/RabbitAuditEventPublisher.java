package github.jiangbyte.io.common.mq.audit;

import github.jiangbyte.io.common.mq.HeiMqConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 基于 RabbitMQ 的审计事件发布实现。
 *
 * Author: Charlie
 */
@RequiredArgsConstructor
public class RabbitAuditEventPublisher implements AuditEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    /** 经 RabbitMQ 发布审计事件。 */
    @Override
    public void publish(AuditEventMessage message) {
        rabbitTemplate.convertAndSend(
                HeiMqConstants.AUDIT_EXCHANGE,
                HeiMqConstants.AUDIT_ROUTING_KEY,
                message
        );
    }
}
