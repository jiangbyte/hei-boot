package github.jiangbyte.io.user.modules.portal.profile.result;

import lombok.Data;

/**
 * 用户组 ID-名称项（由 entity 查询后填充，非 easy-trans Result）。
 *
 * Author: Charlie
 */
@Data
public class GroupIdNameResult {
    private String id;
    private String name;
}
