package github.jiangbyte.io.common.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志模块配置属性：包含审计配置。
 *
 * Author: Charlie
 */
@Data
@ConfigurationProperties(prefix = "hei.log")
public class HeiLogProperties {

    private Audit audit = new Audit();

    @Data
    public static class Audit {
        /** 是否启用异步审计（Redis Stream） */
        private boolean asyncEnabled = true;

        /** Redis Stream 的 Key */
        private String streamKey = "hei:audit:stream";

        /** 消费者组名称 */
        private String groupName = "hei-audit-group";

        /** 消息堆积上限，超过则修剪 */
        private int maxLen = 10000;

        /** 是否启用消费（处理消息落库） */
        private boolean consumeEnabled = true;

        /** 定时修剪间隔（毫秒） */
        private long trimIntervalMs = 60000;
    }
}
