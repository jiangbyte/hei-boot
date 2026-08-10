package github.jiangbyte.io.user.modules.admin.profile.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端更新手机号请求：需密码传输密钥与登录密码确认，并可选启用手机登录。
 *
 * Author: Charlie
 */
@Data
public class PhoneUpdateParam {
    @NotBlank
    private String passwordKeyId;
    @NotBlank
    private String password;
    private String phone;
    private Boolean phoneLoginEnabled = false;
}
