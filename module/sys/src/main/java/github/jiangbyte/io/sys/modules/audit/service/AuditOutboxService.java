package github.jiangbyte.io.sys.modules.audit.service;

import github.jiangbyte.io.common.mq.audit.AuditEventMessage;

/**
 * 审计 Outbox 服务接口：投递与回收。
 *
 * Author: Charlie
 */
public interface AuditOutboxService {

    /** 写入 Outbox 队列。 */
    String enqueue(AuditEventMessage event);

    /** 认领待投递 Outbox。 */
    boolean claim(String outboxId);

    /** 确认 Outbox 投递成功。 */
    void acknowledge(String outboxId);
}
