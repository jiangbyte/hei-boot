package github.jiangbyte.io.sys.modules.audit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 操作审计日志实体，对应表 sys_operation_audit_log。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(
        value = "sys_operation_audit_log",
        autoResultMap = true,
        excludeProperty = {"createdBy", "updatedAt", "updatedBy"})
public class SysOperationAuditLog extends BaseEntity {
    private String module;
    private String resourceType;
    private String resourceId;
    private String action;
    /** 操作内容（可读摘要） */
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
    /** 操作人昵称快照（写入时落库；历史数据查询时批量回显） */
    private String operatorName;
    /** 操作名 */
    private String actionName;
    /** 操作类型：CREATE/UPDATE/DELETE/QUERY/EXPORT/LOGIN/LOGOUT/OTHER */
    private String actionType;
    /** 操作模块展示名 */
    private String moduleLabel;
    /** 执行时长（毫秒） */
    private Integer durationMs;
}
