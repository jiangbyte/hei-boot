package github.jiangbyte.io.sys.modules.audit.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import github.jiangbyte.io.common.notify.mail.MailSenderFacade;
import github.jiangbyte.io.common.notify.push.PushSenderFacade;
import github.jiangbyte.io.sys.config.RuntimeSettings;
import github.jiangbyte.io.sys.config.RuntimeSettingsHolder;
import github.jiangbyte.io.sys.modules.audit.entity.SysAlertLog;
import github.jiangbyte.io.sys.modules.audit.entity.SysOperationAuditLog;
import github.jiangbyte.io.sys.modules.audit.mapper.SysAlertLogMapper;
import github.jiangbyte.io.sys.modules.audit.mapper.SysOperationAuditLogMapper;
import github.jiangbyte.io.sys.modules.config.support.AuditAlertTestSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计告警定时任务：按配置规则扫描并发送告警。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class AuditAlertJob {

    private static final String RULE_BRUTE_FORCE = "audit_volume";

    private final SysOperationAuditLogMapper auditLogMapper;
    private final SysAlertLogMapper alertLogMapper;
    private final PushSenderFacade pushSenderFacade;
    private final MailSenderFacade mailSenderFacade;
    private final AuditAlertTestSender auditAlertTestSender;

    @XxlJob("auditAlertJob")
    public void execute() {
        RuntimeSettings settings = RuntimeSettingsHolder.get();
        if (!settings.getBoolean("AUDIT_ALERT_ENABLED", true)) {
            XxlJobHelper.log("Audit alert disabled via AUDIT_ALERT_ENABLED=false");
            XxlJobHelper.handleSuccess("disabled");
            return;
        }

        int fired = 0;
        if (settings.getBoolean("AUDIT_ALERT_RULE_BRUTE_FORCE", true)) {
            if (evaluateBruteForce(settings)) {
                fired++;
            }
        } else {
            XxlJobHelper.log("RULE_BRUTE_FORCE disabled, skip volume check");
        }

        XxlJobHelper.handleSuccess("done fired=" + fired);
    }

    /**
     * 暴力破解近似检测：分析窗口内审计日志总量超过阈值则告警。
     *
     * @return 是否实际发出告警
     */
    private boolean evaluateBruteForce(RuntimeSettings settings) {
        int windowSeconds = Math.max(60, settings.getInt("AUDIT_ALERT_ANALYSIS_INTERVAL_SECONDS", 60));
        long threshold = Math.max(1L, settings.getLong("AUDIT_ALERT_BRUTE_FORCE_THRESHOLD", 10));
        int windowMinutes = Math.max(1, windowSeconds / 60);
        OffsetDateTime since = OffsetDateTime.now().minusSeconds(windowSeconds);
        Long count = auditLogMapper.selectCount(Wrappers.<SysOperationAuditLog>lambdaQuery()
                .ge(SysOperationAuditLog::getCreatedAt, since));
        long volume = count == null ? 0L : count;
        XxlJobHelper.log("Audit volume in last {}s: {}, threshold={}", windowSeconds, volume, threshold);
        if (volume < threshold) {
            return false;
        }

        // 与 UI/seed 对齐：冷却读 AUDIT_ALERT_ALERT_COOLDOWN_SECONDS
        int cooldownSeconds = Math.max(
                windowSeconds,
                settings.getInt("AUDIT_ALERT_ALERT_COOLDOWN_SECONDS", 1800));
        OffsetDateTime cooldownSince = OffsetDateTime.now().minusSeconds(cooldownSeconds);
        Long recentAlerts = alertLogMapper.selectCount(Wrappers.<SysAlertLog>lambdaQuery()
                .eq(SysAlertLog::getRuleName, RULE_BRUTE_FORCE)
                .ge(SysAlertLog::getCreatedAt, cooldownSince));
        if (recentAlerts != null && recentAlerts > 0) {
            XxlJobHelper.log(
                    "Audit alert suppressed: cooldown={}s, recent={}",
                    cooldownSeconds,
                    recentAlerts);
            return false;
        }

        String summary = "Audit log volume " + volume + " exceeded threshold " + threshold
                + " in last " + windowSeconds + " seconds";
        List<String> notified = notifyChannels(settings, summary);

        SysAlertLog alert = new SysAlertLog();
        alert.setRuleName(RULE_BRUTE_FORCE);
        alert.setSeverity("WARNING");
        alert.setSummary(summary);
        Map<String, Object> details = new HashMap<>();
        details.put("volume", volume);
        details.put("threshold", threshold);
        details.put("window_seconds", windowSeconds);
        details.put("window_minutes", windowMinutes);
        details.put("since", since.toString());
        alert.setDetails(details);
        alert.setNotifiedVia(notified.isEmpty() ? "sys_alert_log" : String.join(",", notified));
        alert.setCreatedAt(OffsetDateTime.now());
        alertLogMapper.insert(alert);
        XxlJobHelper.log("Wrote sys_alert_log id={}", alert.getId());
        return true;
    }

    private List<String> notifyChannels(RuntimeSettings settings, String summary) {
        List<String> notified = new ArrayList<>();
        if (settings.getBoolean("AUDIT_ALERT_NOTIFY_PUSH", true)) {
            try {
                pushSenderFacade.send("审计告警", summary);
                notified.add("push");
            } catch (Exception ex) {
                XxlJobHelper.log("Push notify failed: {}", ex.getMessage());
            }
        }
        if (settings.getBoolean("AUDIT_ALERT_NOTIFY_EMAIL", true)) {
            String to = settings.get("AUDIT_ALERT_NOTIFY_EMAIL_TO", "").trim();
            if (StringUtils.hasText(to)) {
                try {
                    mailSenderFacade.send(to, "审计告警", summary);
                    notified.add("email");
                } catch (Exception ex) {
                    XxlJobHelper.log("Email notify failed: {}", ex.getMessage());
                }
            } else {
                XxlJobHelper.log("Email notify skipped: AUDIT_ALERT_NOTIFY_EMAIL_TO empty");
            }
        }
        if (settings.getBoolean("AUDIT_ALERT_NOTIFY_CUSTOM_WEBHOOK", false)) {
            String webhook = settings.get("AUDIT_ALERT_WEBHOOK_URL", "").trim();
            if (StringUtils.hasText(webhook)) {
                try {
                    auditAlertTestSender.testWebhook(webhook, settings.get("AUDIT_ALERT_WEBHOOK_SECRET", ""));
                    notified.add("webhook");
                } catch (Exception ex) {
                    XxlJobHelper.log("Webhook notify failed: {}", ex.getMessage());
                }
            }
        }
        return notified;
    }
}
