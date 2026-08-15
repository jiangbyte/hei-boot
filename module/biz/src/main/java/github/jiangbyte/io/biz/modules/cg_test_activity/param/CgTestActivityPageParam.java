package github.jiangbyte.io.biz.modules.cg_test_activity.param;

/**
 * Activity分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestActivityPageParam extends PageQuery {
    private String code;
    private String name;
    private String category;
    private String type;
    private String status;
}
