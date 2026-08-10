package github.jiangbyte.io.biz.modules.cg_test_order.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试订单明细分页查询参数（订单 ID 等）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestOrderItemPageParam extends PageQuery {
    private String orderId;
    private String skuCode;
    private String name;
    private String status;
}
