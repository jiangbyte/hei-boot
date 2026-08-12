package github.jiangbyte.io.user.modules.admin.profile.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理端组织信息响应：当前用户角色/部门/用户组的 ID-名称列表。
 *
 * Author: Charlie
 */
@Data
public class OrgInfoResult {
    private List<RoleIdNameResult> roleIdNames = new ArrayList<>();
    private List<DeptIdNameResult> deptIdNames = new ArrayList<>();
    private List<GroupIdNameResult> groupIdNames = new ArrayList<>();
}
