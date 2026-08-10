package github.jiangbyte.io.sys.modules.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditOutbox;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作审计 Outbox 表 MyBatis-Plus Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface SysOperationAuditOutboxMapper extends BaseMapper<SysOperationAuditOutbox> {
}
