package github.jiangbyte.io.biz.modules.cg_test_order.convert;

import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrderItem;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderItemEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 测试订单明细对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrderItem} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestOrderItemConvert {

    /**
     * 将新增参数映射为订单明细实体。
     */
    CgTestOrderItem toEntity(CgTestOrderItemAddParam param);

    /**
     * 将编辑参数覆盖到已有订单明细实体。
     */
    void update(CgTestOrderItemEditParam param, @MappingTarget CgTestOrderItem entity);
}
