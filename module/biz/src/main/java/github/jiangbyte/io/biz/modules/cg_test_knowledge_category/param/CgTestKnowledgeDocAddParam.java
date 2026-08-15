package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

/**
 * 创建知识文档入参。
 *
 * Author: Charlie
 */

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CgTestKnowledgeDocAddParam {
    @NotBlank
    private String categoryId;
    @NotBlank
    private String code;
    @NotBlank
    private String title;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    @NotBlank
    private String summary;
    @NotBlank
    private String content;
    @NotBlank
    private String author;
    private OffsetDateTime publishedAt;
    @NotNull
    private Integer viewCount;
    @NotNull
    private Integer sort;
    @NotNull
    private Boolean isTop;
    private java.util.Map<String, Object> settings;
    private java.util.Map<String, Object> extra;
}
