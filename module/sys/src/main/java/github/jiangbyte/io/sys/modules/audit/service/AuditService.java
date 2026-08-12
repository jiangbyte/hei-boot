package github.jiangbyte.io.sys.modules.audit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.log.audit.AuditEventMessage;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditLog;
import github.jiangbyte.io.sys.modules.audit.param.SysAuditPageParam;

/**
 * 操作审计服务接口：分页与写入。
 *
 * Author: Charlie
 */
public interface AuditService extends IService<SysOperationAuditLog> {

    /** 分页查询审计日志。 */
    Page<SysOperationAuditLog> page(SysAuditPageParam param);

    /** 查询详情。 */
    SysOperationAuditLog detail(String id);

    /** 持久化审计事件。 */
    void persistEvent(AuditEventMessage event);
}
