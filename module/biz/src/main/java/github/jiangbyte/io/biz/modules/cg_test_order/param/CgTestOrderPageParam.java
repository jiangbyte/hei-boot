package github.jiangbyte.io.biz.modules.cg_test_order.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试订单分页查询参数（订单号、名称、客户、状态、类型）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestOrderPageParam extends PageQuery {
    private String orderNo;
    private String name;
    private String customerName;
    private String status;
    private String type;
}
