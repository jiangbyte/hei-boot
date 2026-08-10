package github.jiangbyte.io.user.modules.admin.profile.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端修改密码请求：新密码必填；旧密码或 OTP 按系统改密校验方式选填。
 *
 * Author: Charlie
 */
@Data
public class PasswordUpdateParam {
    @NotBlank
    private String passwordKeyId;
    /**
     * PASSWORD_CHANGE_VERIFY_METHOD 为 OLD_PASSWORD 时必填。
     */
    private String oldPassword;
    @NotBlank
    private String newPassword;
    /**
     * 校验方式为 EMAIL_CODE 或 PHONE_CODE 时必填。
     */
    private String otpCode;
}
