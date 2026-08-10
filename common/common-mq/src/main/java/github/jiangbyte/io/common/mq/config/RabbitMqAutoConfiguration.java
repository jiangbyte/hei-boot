package github.jiangbyte.io.common.mq.config;

import github.jiangbyte.io.common.mq.HeiMqConstants;
import github.jiangbyte.io.common.mq.audit.AuditEventPublisher;
import github.jiangbyte.io.common.mq.audit.RabbitAuditEventPublisher;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * RabbitMQ 自动配置：声明审计交换机/队列绑定，并注册审计发布器。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnClass(RabbitTemplate.class)
@EnableConfigurationProperties(HeiMqProperties.class)
public class RabbitMqAutoConfiguration {

    /** 声明审计交换机。 */
    @Bean
    public DirectExchange auditExchange() {
        return new DirectExchange(HeiMqConstants.AUDIT_EXCHANGE, true, false);
    }

    /** 声明审计死信队列。 */
    @Bean
    public Queue auditDeadLetterQueue() {
        return QueueBuilder.durable(HeiMqConstants.AUDIT_DLQ).build();
    }

    /** 绑定审计死信队列。 */
    @Bean
    public Binding auditDeadLetterBinding(Queue auditDeadLetterQueue, DirectExchange auditExchange) {
        return BindingBuilder.bind(auditDeadLetterQueue)
                .to(auditExchange)
                .with(HeiMqConstants.AUDIT_DLQ_ROUTING_KEY);
    }

    /** 声明审计队列。 */
    @Bean
    public Queue auditQueue() {
        return QueueBuilder.durable(HeiMqConstants.AUDIT_QUEUE)
                .withArgument("x-dead-letter-exchange", HeiMqConstants.AUDIT_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", HeiMqConstants.AUDIT_DLQ_ROUTING_KEY)
                .build();
    }

    /** 绑定审计队列到交换机。 */
    @Bean
    public Binding auditBinding(Queue auditQueue, DirectExchange auditExchange) {
        return BindingBuilder.bind(auditQueue)
                .to(auditExchange)
                .with(HeiMqConstants.AUDIT_ROUTING_KEY);
    }

    /** 注册 Jackson JSON 消息转换器。 */
    @Bean
    @ConditionalOnMissingBean(MessageConverter.class)
    public MessageConverter jacksonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    /** 注册 Rabbit 审计事件发布器。 */
    @Bean
    @ConditionalOnMissingBean
    public AuditEventPublisher auditEventPublisher(RabbitTemplate rabbitTemplate) {
        return new RabbitAuditEventPublisher(rabbitTemplate);
    }
}
