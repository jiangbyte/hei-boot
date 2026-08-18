package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity;

/**
 * KnowledgeCategory实体，对应表 {@code cg_test_knowledge_category}。
 *
 * Author: Charlie
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_knowledge_category", autoResultMap = true)
public class CgTestKnowledgeCategory extends BaseEntity {
    private String parentId;
    private String code;
    private String name;
    private String status;
    private Integer sort;
    private Boolean isVisible;
    private String description;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private java.util.Map<String, Object> extra;
    private String ownerDeptId;

    @TableField(exist = false)
    private List<CgTestKnowledgeCategory> children = new ArrayList<>();
}
