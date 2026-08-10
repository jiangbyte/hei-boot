package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Map;

/**
 * 编辑知识分类的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestKnowledgeCategoryEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
