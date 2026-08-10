package github.jiangbyte.io.message.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 消息模块 Spring Boot 自动配置：扫描 {@code github.jiangbyte.io.message} 包下组件，使反馈、公告/通知与相关 API 随 starter 引入自动注册。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ComponentScan("github.jiangbyte.io.message")
public class MessageAutoConfiguration {
}
