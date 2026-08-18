package github.jiangbyte.io.iam.modules.relation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * IAM 关系实体，对应表 sys_iam_relation。
 * 唯一键：(subject_type, subject_id, relation_type, target_type,
 * target_id, target_key, account_type)。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_iam_relation", autoResultMap = true)
public class SysIamRelation extends BaseEntity {
    private String subjectType;
    private String subjectId;
    private String accountType;
    private String relationType;
    private String targetType;
    private String targetId;
    private String targetKey;
    private String grantMode;
    private String dataScope;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private List<String> customScopeDeptIds;
    private Boolean isPrimary;
    private Integer sort;
    private String status;
    private String description;
    private String reason;
    private OffsetDateTime expiredAt;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> extra;
}
