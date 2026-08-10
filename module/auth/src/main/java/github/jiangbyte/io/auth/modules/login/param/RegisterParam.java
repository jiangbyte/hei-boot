package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 门户用户注册请求参数：账号、加密密码、验证码及可选资料字段。
 *
 * Author: Charlie
 */
@Data
public class RegisterParam {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordKeyId;
    @NotBlank
    private String captchaId;
    @NotBlank
    private String captchaValue;
    private String name;
    private String nickname;
    private String email;
    private String phone;
}
