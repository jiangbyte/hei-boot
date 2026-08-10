package github.jiangbyte.io.common.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息队列模块配置属性（交换机/队列声明等开关）。
 *
 * Author: Charlie
 */
@Data
@ConfigurationProperties(prefix = "hei.mq")
public class HeiMqProperties {

    private Audit audit = new Audit();

    @Data
    public static class Audit {
        /**
         * 为 false 时仍保留审计队列 Bean 供生产者使用，但不注册监听器。
         */
        private boolean consumeEnabled = true;
    }
}
