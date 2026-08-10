package github.jiangbyte.io.biz.modules.cg_test_activity.convert;

import github.jiangbyte.io.biz.modules.cg_test_activity.entity.CgTestActivity;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityAddParam;
import github.jiangbyte.io.biz.modules.cg_test_activity.param.CgTestActivityEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 测试活动对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_activity.entity.CgTestActivity} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestActivityConvert {

    /**
     * 将新增参数映射为活动实体。
     */
    CgTestActivity toEntity(CgTestActivityAddParam param);

    /**
     * 将编辑参数覆盖到已有活动实体。
     */
    void update(CgTestActivityEditParam param, @MappingTarget CgTestActivity entity);
}
