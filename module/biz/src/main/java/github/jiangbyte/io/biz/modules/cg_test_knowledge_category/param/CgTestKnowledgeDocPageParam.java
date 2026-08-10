package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识文档分页查询参数（分类 ID 等）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestKnowledgeDocPageParam extends PageQuery {
    private String categoryId;
    private String code;
    private String title;
    private String type;
    private String status;
}
