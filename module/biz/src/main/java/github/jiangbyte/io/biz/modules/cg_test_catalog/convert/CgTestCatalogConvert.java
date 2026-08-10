package github.jiangbyte.io.biz.modules.cg_test_catalog.convert;

import github.jiangbyte.io.biz.modules.cg_test_catalog.entity.CgTestCatalog;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogAddParam;
import github.jiangbyte.io.biz.modules.cg_test_catalog.param.CgTestCatalogEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 测试目录对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_catalog.entity.CgTestCatalog} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestCatalogConvert {

    /**
     * 将新增参数映射为目录实体。
     */
    CgTestCatalog toEntity(CgTestCatalogAddParam param);

    /**
     * 将编辑参数覆盖到已有目录实体。
     */
    void update(CgTestCatalogEditParam param, @MappingTarget CgTestCatalog entity);
}
