package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 通过邮件重置令牌设置新密码的请求参数。
 *
 * Author: Charlie
 */
@Data
public class ResetPasswordParam {
    /**
     * 可选；重置基于 token。
     */
    private String email;
    @NotBlank
    private String token;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordKeyId;
    @NotBlank
    private String captchaId;
    @NotBlank
    private String captchaValue;
}
