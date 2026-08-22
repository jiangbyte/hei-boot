package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通过手机 OTP 重置密码。
 *
 * Author: Charlie
 */
@Data
public class ResetPasswordByPhoneParam {
    @NotBlank
    private String phone;
    @NotBlank
    private String otpCode;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordKeyId;
    @NotBlank
    private String captchaId;
    @NotBlank
    private String captchaValue;
}
