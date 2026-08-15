package github.jiangbyte.io.biz.modules.cg_test_order.param;

/**
 * 订单明细分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestOrderItemPageParam extends PageQuery {
    private String orderId;
    private String skuCode;
    private String name;
    private String status;
}
