package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

/**
 * KnowledgeCategory分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestKnowledgeCategoryPageParam extends PageQuery {
    private String code;
    private String name;
    private String status;
}
