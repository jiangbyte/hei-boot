package github.jiangbyte.io.common.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * 业务指标辅助：统一注册/递增 Micrometer Counter 与 Timer。
 *
 * Author: Charlie
 */
public class HeiMetrics {

    private final Counter loginSuccess;
    private final Counter loginFailure;
    private final Counter auditPublished;

    public HeiMetrics(MeterRegistry meterRegistry) {
        this.loginSuccess = Counter.builder("hei.auth.login")
                .tag("result", "success")
                .description("Successful login attempts")
                .register(meterRegistry);
        this.loginFailure = Counter.builder("hei.auth.login")
                .tag("result", "failure")
                .description("Failed login attempts")
                .register(meterRegistry);
        this.auditPublished = Counter.builder("hei.audit.published")
                .description("Audit events published to MQ")
                .register(meterRegistry);
    }

    /** 递增登录成功计数。 */
    public void incrementLoginSuccess() {
        loginSuccess.increment();
    }

    /** 递增登录失败计数。 */
    public void incrementLoginFailure() {
        loginFailure.increment();
    }

    /** 递增审计事件发布计数。 */
    public void incrementAuditPublished() {
        auditPublished.increment();
    }
}
