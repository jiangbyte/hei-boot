package github.jiangbyte.io.biz.modules.cg_test_order.convert;

import github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrder;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderAddParam;
import github.jiangbyte.io.biz.modules.cg_test_order.param.CgTestOrderEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 测试订单对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_order.entity.CgTestOrder} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestOrderConvert {

    /**
     * 将新增参数映射为订单实体。
     */
    CgTestOrder toEntity(CgTestOrderAddParam param);

    /**
     * 将编辑参数覆盖到已有订单实体。
     */
    void update(CgTestOrderEditParam param, @MappingTarget CgTestOrder entity);
}
