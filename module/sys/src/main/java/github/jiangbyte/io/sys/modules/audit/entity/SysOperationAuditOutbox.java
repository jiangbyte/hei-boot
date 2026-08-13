package github.jiangbyte.io.sys.modules.audit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 审计 outbox 实体，对应表 sys_operation_audit_outbox。
 *
 * Author: Charlie
 */
@Data
@TableName("sys_operation_audit_outbox")
public class SysOperationAuditOutbox {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 事件 JSON */
    private String payload;

    /** PENDING | CLAIMED | DONE | DEAD */
    private String status;

    private Integer attempts;

    private OffsetDateTime createdAt;

    private OffsetDateTime claimedAt;
}
