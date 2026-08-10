package github.jiangbyte.io.iam.modules.dept.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptPageParam extends PageQuery {

    private String name;
    private String category;
    private String status;
    private String parentId;
}
