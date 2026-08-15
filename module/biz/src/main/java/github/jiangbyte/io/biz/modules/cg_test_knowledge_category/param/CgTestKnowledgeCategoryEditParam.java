package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

/**
 * 编辑KnowledgeCategory入参。
 *
 * Author: Charlie
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Map;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Integer sort;
    @NotNull
    private Boolean isVisible;
    @NotBlank
    private String description;
    private java.util.Map<String, Object> extra;
}
