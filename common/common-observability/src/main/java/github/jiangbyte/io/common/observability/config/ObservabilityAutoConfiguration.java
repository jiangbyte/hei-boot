package github.jiangbyte.io.common.observability.config;

import github.jiangbyte.io.common.observability.HeiMetrics;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 可观测性自动配置：注册 HeiMetrics 等监控辅助 Bean。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnClass(MeterRegistry.class)
public class ObservabilityAutoConfiguration {

    /** 注册 HeiMetrics。 */
    @Bean
    @ConditionalOnBean(MeterRegistry.class)
    @ConditionalOnMissingBean
    public HeiMetrics heiMetrics(MeterRegistry meterRegistry) {
        return new HeiMetrics(meterRegistry);
    }
}
