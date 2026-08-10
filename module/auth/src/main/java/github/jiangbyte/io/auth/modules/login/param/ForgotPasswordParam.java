package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 忘记密码请求参数：校验验证码后，向绑定邮箱发送重置链接。
 *
 * Author: Charlie
 */
@Data
public class ForgotPasswordParam {
    @NotBlank
    private String email;
    @NotBlank
    private String captchaId;
    @NotBlank
    private String captchaValue;
}
