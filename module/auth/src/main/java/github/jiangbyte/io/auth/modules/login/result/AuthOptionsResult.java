package github.jiangbyte.io.auth.modules.login.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录页公开配置：可用登录方式、注册开关/通道、强制绑定、三方入口与版权文案。
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
    /** 门户注册是否允许用户名通道 */
    private Boolean registerAllowAccount = false;
    /** 门户注册是否允许邮箱通道 */
    private Boolean registerAllowEmail = false;
    /** 门户注册是否允许手机通道 */
    private Boolean registerAllowPhone = false;
    /** 用户名通道注册是否必填邮箱 */
    private Boolean registerRequireEmail = false;
    /** 用户名通道注册是否必填手机号 */
    private Boolean registerRequirePhone = false;
    /** 当前端是否强制绑定邮箱 */
    private Boolean forceBindEmail = false;
    /** 当前端是否强制绑定手机 */
    private Boolean forceBindPhone = false;
    private List<OauthProviderOptionResult> oauthProviders = new ArrayList<>();
    private String passwordChangeVerifyMethod = "OLD_PASSWORD";
    private String copyrightText = "";
    private String copyrightUrl = "";
}
