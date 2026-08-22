package github.jiangbyte.io.profile.modules.admin.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import github.jiangbyte.io.profile.modules.identity.result.IdentityStatusResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 管理端当前用户 me 响应：账号会话摘要、展示信息、组织名称与完整资料。
 *
 * Author: Charlie
 */
@Data
public class MeResult {
    private String accountId;
    private String account;
    private AccountType accountType;
    private String nickname;
    private String avatar;
    private IdentityStatusResult identity;
    private List<String> roleIds = new ArrayList<>();
    private List<String> deptIds = new ArrayList<>();
    private List<String> groupIds = new ArrayList<>();
    private List<RoleIdNameResult> roleIdNames = new ArrayList<>();
    private List<DeptIdNameResult> deptIdNames = new ArrayList<>();
    private List<GroupIdNameResult> groupIdNames = new ArrayList<>();
    private Set<String> permissionKeys;
    private Boolean passwordExpired = false;
    /** 是否需强制绑定邮箱（硬拦截） */
    private Boolean forceBindEmail = false;
    /** 是否需强制绑定手机（硬拦截） */
    private Boolean forceBindPhone = false;
    /** 是否需强制完成实名认证（硬拦截） */
    private Boolean forceBindIdentity = false;
    private UserProfileResult profile;
}
