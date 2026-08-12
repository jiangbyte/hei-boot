package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 门户用户注册请求：通道 ACCOUNT / EMAIL / PHONE，均需密码与图形验证码；邮箱/手机通道另需 OTP。
 *
 * Author: Charlie
 */
@Data
public class RegisterParam {

    /** ACCOUNT | EMAIL | PHONE */
    @NotBlank
    private String registerChannel;

    @Size(min = 3, max = 64)
    private String account;

    @Size(max = 128)
    private String email;

    @Size(max = 32)
    private String phone;

    /** 邮箱/手机通道注册 OTP */
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
