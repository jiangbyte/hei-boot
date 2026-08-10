package github.jiangbyte.io.common.security.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 限流配置属性：开关、窗口、阈值与白名单等。
 *
 * Author: Charlie
 */
@ConfigurationProperties(prefix = "hei.rate-limit")
public class RateLimitProperties {

    /**
     * 为 false 时关闭限流（存在 Redisson 时默认 true）。
     */
    private boolean enabled = true;

    /**
     * 为 true 时使用 X-Forwarded-For / X-Real-IP。仅在可信代理后开启。
     */
    private boolean trustForwardedHeaders = false;

    private List<Rule> rules = defaultRules();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTrustForwardedHeaders() {
        return trustForwardedHeaders;
    }

    public void setTrustForwardedHeaders(boolean trustForwardedHeaders) {
        this.trustForwardedHeaders = trustForwardedHeaders;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    private static List<Rule> defaultRules() {
        List<Rule> defaults = new ArrayList<>();
        // 认证相关（较严）
        defaults.add(rule(".*/(admin|portal)/login$", 20, 60));
        defaults.add(rule(".*/portal/register$", 20, 60));
        defaults.add(rule(".*/(admin|portal)/(forgot-password|reset-password)$", 20, 60));
        defaults.add(rule(".*/(admin|portal)/captcha$", 20, 60));
        // 探针 / 公开读（较宽；放在 catch-all 之前）
        defaults.add(rule(".*/internal/health/.*", 120, 60));
        defaults.add(rule(".*/files$", 300, 60));
        // 热点写路径
        defaults.add(rule(".*/sys/file/upload$", 30, 60));
        // 通用 /api catch-all（须放最后）
        defaults.add(rule("^/api/", 200, 60));
        return defaults;
    }

    private static Rule rule(String pathPattern, int limit, int windowSeconds) {
        Rule rule = new Rule();
        rule.setPathPattern(pathPattern);
        rule.setLimit(limit);
        rule.setWindowSeconds(windowSeconds);
        return rule;
    }

    public static class Rule {
        private String pathPattern;
        private int limit = 20;
        private int windowSeconds = 60;

        public String getPathPattern() {
            return pathPattern;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getWindowSeconds() {
            return windowSeconds;
        }

        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }
    }
}
