package github.jiangbyte.io.iam.modules.group.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysGroupPageParam extends PageQuery {

    private String name;
    private String status;
}
