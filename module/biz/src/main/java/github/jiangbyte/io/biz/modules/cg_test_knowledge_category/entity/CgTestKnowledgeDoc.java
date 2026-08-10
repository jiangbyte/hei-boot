package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 知识文档实体，对应表 {@code cg_test_knowledge_doc}；归属分类并存储文档内容/附件等。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_knowledge_doc", autoResultMap = true)
public class CgTestKnowledgeDoc extends CommonEntity {
    private String categoryId;
    private String code;
    private String title;
    private String type;
    private String status;
    private String summary;
    private String content;
    private String author;
    private OffsetDateTime publishedAt;
    private Integer viewCount;
    private Integer sort;
    private Boolean isTop;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> settings;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> extra;
}
