package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

/**
 * 知识文档分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestKnowledgeDocPageParam extends PageQuery {
    private String categoryId;
    private String code;
    private String title;
    private String type;
    private String status;
}
