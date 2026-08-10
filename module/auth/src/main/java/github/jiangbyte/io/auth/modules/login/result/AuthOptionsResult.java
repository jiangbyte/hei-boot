package github.jiangbyte.io.auth.modules.login.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;

/**
 * 登录页公开配置：可用登录方式、注册开关、改密校验方式与版权文案。
 *
 * Author: Charlie
 */
@Data
public class AuthOptionsResult {
    private AccountType accountType;
    private Boolean allowAccount = true;
    private Boolean allowEmail = true;
    private Boolean allowPhone = true;
    private Boolean allowOtp = true;
    private Boolean registerEnabled = false;
    private Boolean registerRequirePhone = false;
    private Boolean registerRequireEmail = false;
    private String passwordChangeVerifyMethod = "OLD_PASSWORD";
    private String copyrightText = "";
    private String copyrightUrl = "";
}
