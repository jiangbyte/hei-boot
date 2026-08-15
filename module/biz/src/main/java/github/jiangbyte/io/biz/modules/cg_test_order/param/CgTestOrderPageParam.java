package github.jiangbyte.io.biz.modules.cg_test_order.param;

/**
 * Order分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestOrderPageParam extends PageQuery {
    private String orderNo;
    private String name;
    private String customerName;
    private String status;
    private String type;
}
