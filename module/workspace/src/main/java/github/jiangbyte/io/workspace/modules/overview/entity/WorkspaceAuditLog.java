package github.jiangbyte.io.workspace.modules.overview.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作台只读审计日志投影，对应表 sys_operation_audit_log。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_operation_audit_log", excludeProperty = {"createdBy", "updatedAt", "updatedBy"})
public class WorkspaceAuditLog extends BaseEntity {
    private String module;
    private String action;
    private String summary;
    private String accountId;
    private String accountType;
    private String ip;
    private String userAgent;
    private Boolean success;
    private String operatorName;
    private String actionName;
    private String actionType;
    private String moduleLabel;
    private Integer durationMs;
    private String resourceId;
}
