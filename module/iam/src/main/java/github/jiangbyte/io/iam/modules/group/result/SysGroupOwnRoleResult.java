package github.jiangbyte.io.iam.modules.group.result;

import github.jiangbyte.io.iam.modules.role.entity.SysRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组已拥有角色结果（角色 id 列表）。
 *
 * Author: Charlie
 */
@Data
public class SysGroupOwnRoleResult {

    private String id;
    private List<SysRole> roles = new ArrayList<>();
    private List<String> roleIds = new ArrayList<>();
}
