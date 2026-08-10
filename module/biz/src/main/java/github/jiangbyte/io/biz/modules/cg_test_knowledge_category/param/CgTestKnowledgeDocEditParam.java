package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 编辑知识文档的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestKnowledgeDocEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
    private Integer viewCount;
    private Integer sort;
    private Boolean isTop;
    private java.util.Map<String, Object> settings;
    private java.util.Map<String, Object> extra;
}
