package github.jiangbyte.io.common.mq;

/**
 * 消息队列常量：审计交换机、队列与路由键等约定名称。
 *
 * Author: Charlie
 */
public final class HeiMqConstants {

    public static final String AUDIT_EXCHANGE = "hei.audit.exchange";
    public static final String AUDIT_QUEUE = "hei.audit.queue";
    public static final String AUDIT_ROUTING_KEY = "hei.audit.event";
    public static final String AUDIT_DLQ = "hei.audit.queue.dlq";
    public static final String AUDIT_DLQ_ROUTING_KEY = "hei.audit.event.dlq";

    private HeiMqConstants() {
    }
}
