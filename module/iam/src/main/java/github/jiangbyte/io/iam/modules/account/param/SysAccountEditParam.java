package github.jiangbyte.io.iam.modules.account.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 管理端编辑账号入参（含 id 与可更新字段）。
 *
 * Author: Charlie
 */
@Data
public class SysAccountEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String account;

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
