package github.jiangbyte.io.iam.modules.position.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPositionPageParam extends PageQuery {

    private String name;
    private String category;
    private String status;
}
