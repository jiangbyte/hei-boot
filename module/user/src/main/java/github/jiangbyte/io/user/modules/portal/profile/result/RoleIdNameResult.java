package github.jiangbyte.io.user.modules.portal.profile.result;

import lombok.Data;

/**
 * 角色 ID-名称项（由 entity 查询后填充，非 easy-trans Result）。
 *
 * Author: Charlie
 */
@Data
public class RoleIdNameResult {
    private String id;
    private String name;
}
