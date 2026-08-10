package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.convert;

import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeCategory;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeCategoryEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 知识分类对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeCategory} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestKnowledgeCategoryConvert {

    /**
     * 将新增参数映射为分类实体。
     */
    CgTestKnowledgeCategory toEntity(CgTestKnowledgeCategoryAddParam param);

    /**
     * 将编辑参数覆盖到已有分类实体。
     */
    void update(CgTestKnowledgeCategoryEditParam param, @MappingTarget CgTestKnowledgeCategory entity);
}
