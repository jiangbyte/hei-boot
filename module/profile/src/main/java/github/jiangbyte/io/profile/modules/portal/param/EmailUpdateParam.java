package github.jiangbyte.io.profile.modules.portal.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 门户端更新邮箱请求：需密码传输密钥与登录密码确认，并可选启用邮箱登录。
 *
 * Author: Charlie
 */
@Data
public class EmailUpdateParam {
    @NotBlank
    private String passwordKeyId;
    @NotBlank
    private String password;
    private String email;
    private Boolean emailLoginEnabled = false;
    /** 绑定/换绑时必填的邮箱 OTP */
    private String otpCode;
}
