package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识分类分页查询参数（编码、名称、状态）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestKnowledgeCategoryPageParam extends PageQuery {
    private String code;
    private String name;
    private String status;
}
