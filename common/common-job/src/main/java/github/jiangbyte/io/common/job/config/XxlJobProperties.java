package github.jiangbyte.io.common.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XXL-JOB 相关配置属性（开关、Admin 地址、执行器端口与日志保留等）。
 *
 * Author: Charlie
 */
@Data
@ConfigurationProperties(prefix = "hei.xxl-job")
public class XxlJobProperties {

    private boolean enabled = true;
    private String accessToken = "";
    private Admin admin = new Admin();
    private Executor executor = new Executor();

    @Data
    public static class Admin {
        private String addresses = "http://127.0.0.1:8080/xxl-job-admin";
    }

    @Data
    public static class Executor {
        private String appname = "hei-boot-admin";
        private int port = 9999;
        private String logpath = "./logs/xxl-job";
        private int logretentiondays = 30;
    }
}
