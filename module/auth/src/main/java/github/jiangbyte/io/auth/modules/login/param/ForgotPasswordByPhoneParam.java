package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通过手机找回密码：校验图形验证码后向绑定手机发送重置 OTP。
 *
 * Author: Charlie
 */
@Data
public class ForgotPasswordByPhoneParam {
    @NotBlank
    private String phone;
    @NotBlank
    private String captchaId;
    @NotBlank
    private String captchaValue;
}
