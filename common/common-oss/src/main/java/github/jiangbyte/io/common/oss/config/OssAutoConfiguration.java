package github.jiangbyte.io.common.oss.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 对象存储自动配置入口：按类型装配 local / S3 实现。
 *
 * Author: Charlie
 */
@AutoConfiguration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {
}
