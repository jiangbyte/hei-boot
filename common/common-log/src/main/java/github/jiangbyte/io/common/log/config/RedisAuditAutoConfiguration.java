package github.jiangbyte.io.common.log.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import github.jiangbyte.io.common.log.audit.AuditEventHandler;
import github.jiangbyte.io.common.log.audit.AuditEventPublisher;
import github.jiangbyte.io.common.log.audit.RedisAuditEventConsumer;
import github.jiangbyte.io.common.log.audit.RedisAuditEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * Redis 审计自动配置：注册审计发布器和消费者。
 *
 * Author: Charlie
 */
@AutoConfiguration
@EnableScheduling
@ConditionalOnClass({StringRedisTemplate.class, RedisConnectionFactory.class})
@EnableConfigurationProperties(HeiLogProperties.class)
public class RedisAuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper auditObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    @ConditionalOnMissingBean(AuditEventPublisher.class)
    public AuditEventPublisher auditEventPublisher(
            StringRedisTemplate stringRedisTemplate,
            ObjectMapper objectMapper,
            HeiLogProperties heiLogProperties) {
        return new RedisAuditEventPublisher(
                stringRedisTemplate,
                objectMapper,
                heiLogProperties.getAudit());
    }

    @Bean
    @ConditionalOnMissingBean(RedisAuditEventConsumer.class)
    public RedisAuditEventConsumer auditEventConsumer(
            StringRedisTemplate stringRedisTemplate,
            ObjectMapper objectMapper,
            HeiLogProperties heiLogProperties,
            @Autowired(required = false) List<AuditEventHandler> handlers) {
        return new RedisAuditEventConsumer(
                stringRedisTemplate,
                objectMapper,
                heiLogProperties.getAudit(),
                handlers);
    }
}
