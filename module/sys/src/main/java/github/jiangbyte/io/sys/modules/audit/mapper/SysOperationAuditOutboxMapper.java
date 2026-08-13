package github.jiangbyte.io.sys.modules.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditOutbox;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 审计 outbox 表 Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface SysOperationAuditOutboxMapper extends BaseMapper<SysOperationAuditOutbox> {

    /**
     * 认领一批 PENDING（或超时 CLAIMED）记录，PostgreSQL SKIP LOCKED + RETURNING。
     */
    @Select("""
            UPDATE sys_operation_audit_outbox
            SET status = 'CLAIMED',
                claimed_at = now(),
                attempts = attempts + 1
            WHERE id IN (
                SELECT id FROM sys_operation_audit_outbox
                WHERE status = 'PENDING'
                   OR (status = 'CLAIMED' AND claimed_at < #{staleBefore})
                ORDER BY created_at
                LIMIT #{limit}
                FOR UPDATE SKIP LOCKED
            )
            RETURNING id, payload, status, attempts, created_at, claimed_at
            """)
    List<SysOperationAuditOutbox> claimBatch(
            @Param("limit") int limit,
            @Param("staleBefore") OffsetDateTime staleBefore);
}
