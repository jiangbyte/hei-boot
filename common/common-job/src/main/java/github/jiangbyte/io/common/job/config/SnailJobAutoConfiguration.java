package github.jiangbyte.io.common.job.config;

import com.aizuda.snailjob.client.starter.EnableSnailJob;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * SnailJob 客户端自动配置：在 snail-job.enabled=true 时启用。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "snail-job", name = "enabled", havingValue = "true")
@EnableSnailJob
public class SnailJobAutoConfiguration {
}
