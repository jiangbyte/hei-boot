package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

import lombok.Data;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;

/**
 * 创建知识分类的请求参数。
 *
 * Author: Charlie
 */
@Data
public class CgTestKnowledgeCategoryAddParam {
    @NotBlank
    private String parentId;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String status;
    private Integer sort;
    private Boolean isVisible;
    @NotBlank
    private String description;
    private java.util.Map<String, Object> extra;
}
