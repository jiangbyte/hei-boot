package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity;

/**
 * 知识文档实体，对应表 {@code cg_test_knowledge_doc}。
 *
 * Author: Charlie
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_knowledge_doc", autoResultMap = true)
public class CgTestKnowledgeDoc extends BaseEntity {
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
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private java.util.Map<String, Object> settings;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private java.util.Map<String, Object> extra;
}
