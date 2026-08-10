package github.jiangbyte.io.iam.modules.account.result;

import github.jiangbyte.io.iam.modules.role.entity.SysRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号已拥有角色结果（角色 id 列表）。
 *
 * Author: Charlie
 */
@Data
public class SysAccountOwnRoleResult {

    private String id;
    private List<SysRole> roles = new ArrayList<>();
    private List<String> roleIds = new ArrayList<>();
}
