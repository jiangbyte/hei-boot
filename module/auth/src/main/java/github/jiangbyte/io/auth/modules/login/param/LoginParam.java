package github.jiangbyte.io.auth.modules.login.param;

import github.jiangbyte.io.common.core.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求参数：支持账号/邮箱/手机 + 密码或 OTP，并携带图形验证码。
 *
 * Author: Charlie
 */
@Data
public class LoginParam {

    @NotBlank
    private String account;

    /**
     * loginMode 为 PASSWORD 时必填。
     */
    private String password;

    /** loginMode 为 PASSWORD 时必填。 */
    private String passwordKeyId;

    @NotBlank
    private String captchaId;

    @NotBlank
    private String captchaValue;

    private String identityType = "ACCOUNT";

    /**
     * PASSWORD 或 OTP
     */
    private String loginMode = "PASSWORD";

    private String otpCode;

    private Boolean rememberMe = true;

    /** 由控制器设置；非强类型接口的客户端载荷字段。 */
    private AccountType accountType = AccountType.ADMIN;
}
