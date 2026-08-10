package github.jiangbyte.io.auth.modules.login.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 当前登录用户摘要：账号标识、角色权限集合及密码是否过期等。
 *
 * Author: Charlie
 */
@Data
public class CurrentUserResult {
    private String accountId;
    private String account;
    private AccountType accountType;
    private Set<String> roles;
    private Set<String> permissions;
    private List<String> roleIds;
    private List<String> deptIds;
    private List<String> groupIds;
    private List<String> resourceIds;
    private List<String> buttonCodes;
    private Boolean passwordExpired;
}
