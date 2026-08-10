package github.jiangbyte.io.iam.modules.role.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageParam extends PageQuery {

    private String code;
    private String name;
    private String category;
    private String scopeType;
    private String status;
}
