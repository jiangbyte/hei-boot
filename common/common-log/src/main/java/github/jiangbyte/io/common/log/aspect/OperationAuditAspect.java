package github.jiangbyte.io.common.log.aspect;

import github.jiangbyte.io.common.core.trace.TraceIdHolder;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.log.audit.AuditEventMessage;
import github.jiangbyte.io.common.log.audit.AuditEventPublisher;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.common.web.log.RequestLogMdc;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;

/**
 * 操作审计切面：拦截 @OperationAudit，组装审计消息并经 AuditEventPublisher 发布。
 *
 * Author: Charlie
 */
@Aspect
@RequiredArgsConstructor
public class OperationAuditAspect {

    private final AuditEventPublisher auditEventPublisher;

    /** 记录并发布操作审计事件。 */
    @Around("@annotation(operationAudit)")
    public Object recordOperationAudit(ProceedingJoinPoint joinPoint, OperationAudit operationAudit) throws Throwable {
        HttpServletRequest request = currentRequest();
        Object result = joinPoint.proceed();
        publishAuditEvent(operationAudit, request);
        return result;
    }

    private void publishAuditEvent(OperationAudit operationAudit, HttpServletRequest request) {
        LoginUser loginUser = LoginHelper.currentUser().orElse(null);
        String action = operationAudit.action();
        if (action == null || action.isBlank()) {
            action = request != null ? request.getMethod().toLowerCase() : "unknown";
        }
        AuditEventMessage message = AuditEventMessage.builder()
                .resourceType(operationAudit.resourceType())
                .action(action)
                .method(request != null ? request.getMethod() : null)
                .path(request != null ? request.getRequestURI() : null)
                .statusCode(200)
                .accountId(loginUser != null ? loginUser.getAccountId() : null)
                .accountType(loginUser != null && loginUser.getAccountType() != null
                        ? loginUser.getAccountType().name().toLowerCase()
                        : null)
                .requestId(resolveRequestId())
                .ip(request != null ? request.getRemoteAddr() : null)
                .userAgent(request != null ? request.getHeader("User-Agent") : null)
                .occurredAt(Instant.now())
                .build();
        auditEventPublisher.publish(message);
    }

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    private static String resolveRequestId() {
        String fromMdc = MDC.get(RequestLogMdc.REQUEST_ID);
        if (StringUtils.hasText(fromMdc)) {
            return fromMdc;
        }
        return TraceIdHolder.get();
    }
}
