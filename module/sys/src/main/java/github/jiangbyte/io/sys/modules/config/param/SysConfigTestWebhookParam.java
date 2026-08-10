package github.jiangbyte.io.sys.modules.config.param;

import lombok.Data;

/**
 * 测试审计告警 Webhook 入参。
 *
 * Author: Charlie
 */
@Data
public class SysConfigTestWebhookParam {

    private String webhookUrl;
    private String webhookSecret;
}
