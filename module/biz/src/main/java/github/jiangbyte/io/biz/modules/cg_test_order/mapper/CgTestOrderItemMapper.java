package github.jiangbyte.io.biz.modules.cg_test_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试订单明细表 {@code cg_test_order_item} 的 MyBatis-Plus Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface CgTestOrderItemMapper extends BaseMapper<CgTestOrderItem> {
}
