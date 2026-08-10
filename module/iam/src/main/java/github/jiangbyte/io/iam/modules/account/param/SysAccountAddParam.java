package github.jiangbyte.io.iam.modules.account.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端创建账号入参（基础字段、资料与可选登录身份）。
 *
 * Author: Charlie
 */
@Data
public class SysAccountAddParam {

    @NotBlank
    private String account;

    /**
     * 配置了 {@code AUTH_DEFAULT_PASSWORD} 时允许为空。
     */
    private String password;

    private String passwordKeyId;

    @NotBlank
    private String accountType;

    private String accountStatus = "ENABLED";
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
    private Boolean emailLoginEnabled = false;
    private Boolean phoneLoginEnabled = false;
    private String emailIdentity;
    private String phoneIdentity;
    private Boolean emailIdentityVerified = false;
    private Boolean phoneIdentityVerified = false;
    private String emailIdentityBindStatus = "BOUND";
    private String phoneIdentityBindStatus = "BOUND";
    private String remark;
}
