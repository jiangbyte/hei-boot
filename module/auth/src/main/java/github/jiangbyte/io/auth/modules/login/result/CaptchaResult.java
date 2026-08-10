package github.jiangbyte.io.auth.modules.login.result;

import lombok.Data;

/**
 * 图形验证码响应：验证码 ID、Base64 图片内容与 MIME 类型。
 *
 * Author: Charlie
 */
@Data
public class CaptchaResult {
    private String captchaId;
    private String imageBase64;
    private String imageType = "image/svg+xml";
}
