package github.jiangbyte.io.biz.modules.cg_test_activity.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试活动分页查询参数（编码、名称、分类、类型、状态）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestActivityPageParam extends PageQuery {
    private String code;
    private String name;
    private String category;
    private String type;
    private String status;
}
