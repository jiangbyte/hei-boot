package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.convert;

import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeDoc;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocAddParam;
import github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param.CgTestKnowledgeDocEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 知识文档对象转换：新增/编辑参数与 {@link github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity.CgTestKnowledgeDoc} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CgTestKnowledgeDocConvert {

    /**
     * 将新增参数映射为文档实体。
     */
    CgTestKnowledgeDoc toEntity(CgTestKnowledgeDocAddParam param);

    /**
     * 将编辑参数覆盖到已有文档实体。
     */
    void update(CgTestKnowledgeDocEditParam param, @MappingTarget CgTestKnowledgeDoc entity);
}
