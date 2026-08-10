package github.jiangbyte.io.sys.modules.audit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(
        value = "sys_operation_audit_outbox",
        excludeProperty = {"createdBy", "updatedAt", "updatedBy"})

/**
 * 操作审计 Outbox 实体，对应表 sys_operation_audit_outbox。
 *
 * Author: Charlie
 */
public class SysOperationAuditOutbox extends CommonEntity {
    private String payload;
    private String status;
    private Integer attempts;
    private OffsetDateTime claimedAt;
}
