package github.jiangbyte.io.iam.modules.account.result;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 账号详情/分页行结果：合并账号主数据、资料与身份展示字段。
 *
 * Author: Charlie
 */
@Data
public class SysAccountResult {

    private String id;
    private String account;
    private String accountType;
    private String accountStatus;
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
    private String emailIdentityBindStatus;
    private String phoneIdentityBindStatus;
    private List<AccountIdentityResult> identities = new ArrayList<>();
    private List<AccountOauthBindingResult> oauthBindings = new ArrayList<>();
    private String remark;
    private OffsetDateTime cancelledAt;
    private String cancelledBy;
    private String cancelReason;
    private String lastLoginIp;
    private String lastLoginAddress;
    private OffsetDateTime lastLoginTime;
    private String lastLoginDevice;
    private String latestLoginIp;
    private String latestLoginAddress;
    private OffsetDateTime latestLoginTime;
    private String latestLoginDevice;
    private OffsetDateTime createdAt;
    private String createdBy;
    private OffsetDateTime updatedAt;
    private String updatedBy;
}
