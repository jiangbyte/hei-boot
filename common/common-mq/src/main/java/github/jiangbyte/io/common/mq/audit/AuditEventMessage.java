package github.jiangbyte.io.common.mq.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * 审计事件消息体：资源、动作、操作者与请求上下文等字段。
 *
 * Author: Charlie
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEventMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String resourceType;
    private String action;
    private String method;
    private String path;
    private Integer statusCode;
    private String accountId;
    private String accountType;
    private String requestId;
    private String ip;
    private String userAgent;
    private Instant occurredAt;
    /**
     * 持久化 outbox 行 id；在 MQ 发布前写入，供消费者 claim/ack。
     */
    private String outboxId;
}
