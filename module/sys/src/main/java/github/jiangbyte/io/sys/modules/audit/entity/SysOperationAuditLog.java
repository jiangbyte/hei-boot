package github.jiangbyte.io.sys.modules.audit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(
        value = "sys_operation_audit_log",
        autoResultMap = true,
        excludeProperty = {"createdBy", "updatedAt", "updatedBy"})

/**
 * 操作审计日志实体，对应表 sys_operation_audit_log。
 *
 * Author: Charlie
 */
public class SysOperationAuditLog extends BaseEntity {
    private String module;
    private String resourceType;
    private String resourceId;
    private String action;
    private String summary;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> beforeData;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> afterData;
    private String accountId;
    private String accountType;
    private String requestId;
    private String ip;
    private String userAgent;
    private Boolean success;
    private String errorMessage;
}
