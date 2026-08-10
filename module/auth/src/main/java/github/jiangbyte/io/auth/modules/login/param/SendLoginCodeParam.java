package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发送登录 OTP 验证码的请求参数（邮箱或手机）。
 *
 * Author: Charlie
 */
@Data
public class SendLoginCodeParam {

    @NotBlank
    private String target;

    /** EMAIL 或 PHONE */
    @NotBlank
    private String channel;

    @NotBlank
    private String captchaId;

    @NotBlank
    private String captchaValue;
}
