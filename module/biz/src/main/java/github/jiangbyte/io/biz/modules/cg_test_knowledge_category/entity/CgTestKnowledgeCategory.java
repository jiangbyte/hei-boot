package github.jiangbyte.io.biz.modules.cg_test_knowledge_category.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * 知识分类实体，对应表 {@code cg_test_knowledge_category}；支持父子层级与状态。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_knowledge_category", autoResultMap = true)
public class CgTestKnowledgeCategory extends CommonEntity {
    private String parentId;
    private String code;
    private String name;
    private String status;
    private Integer sort;
    private Boolean isVisible;
    private String description;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> extra;
    private String ownerDeptId;

    @TableField(exist = false)
    private List<CgTestKnowledgeCategory> children = new ArrayList<>();
}
