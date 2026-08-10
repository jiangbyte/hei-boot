package github.jiangbyte.io.common.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全模块配置属性：匿名 URL、CORS、CSRF、HSTS/CSP 与转发头信任等。
 *
 * Author: Charlie
 */
@ConfigurationProperties(prefix = "hei.security")
public class HeiSecurityProperties {

    /**
     * 额外匿名 URL 模式（Ant 风格），与条件内置规则合并。
     */
    private List<String> ignoreUrls = new ArrayList<>();

    /** 无需鉴权暴露 Swagger / Knife4j / OpenAPI（生产环境请关闭）。 */
    private boolean exposeDocs = true;

    /** 无需 Sa-Token 暴露 /actuator/**（生产环境请关闭；配合网络 ACL）。 */
    private boolean exposeActuator = true;

    /** 无需 Sa-Token 暴露 /druid/**（生产环境请关闭）。 */
    private boolean exposeDruid = true;

    /**
     * 为 true 时，RateLimit / Filter 可信任 X-Forwarded-For / X-Real-IP。
     * 除非位于会覆写这些头的可信反向代理之后，否则保持 false。
     */
    private boolean trustForwardedHeaders = false;

    /**
     * 允许的 CORS 源（由 Sa-Token {@code SaStrategy.corsHandle} 使用）。
     * 为空：本地默认 5173/5174/5163 + 追加 {@code hei.app.frontend-base-url}。
     * 含 {@code *}：通配且强制关闭 credentials（对齐 fastapi）。
     */
    private List<String> corsAllowedOrigins = new ArrayList<>();

    /**
     * Cookie 会话下的 CSRF 守卫（要求 X-Requested-With）。默认关闭以兼容 fastapi Web；
     * 仅当 {@code sa-token.is-read-cookie=true} 且本项为 true 时生效。
     */
    private boolean cookieCsrfEnabled = false;

    /**
     * API 响应的 HSTS max-age（秒）。{@code 0} 不写该头（默认）。
     * 仅在经 HTTPS 访问服务时开启。
     */
    private long hstsMaxAgeSeconds = 0;

    private boolean hstsIncludeSubDomains = true;

    private boolean hstsPreload = false;

    /** API 响应可选 CSP；为空则跳过该头（通常由 SPA nginx 负责 CSP）。 */
    private String contentSecurityPolicy = "";

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls == null ? new ArrayList<>() : ignoreUrls;
    }

    public boolean isExposeDocs() {
        return exposeDocs;
    }

    public void setExposeDocs(boolean exposeDocs) {
        this.exposeDocs = exposeDocs;
    }

    public boolean isExposeActuator() {
        return exposeActuator;
    }

    public void setExposeActuator(boolean exposeActuator) {
        this.exposeActuator = exposeActuator;
    }

    public boolean isExposeDruid() {
        return exposeDruid;
    }

    public void setExposeDruid(boolean exposeDruid) {
        this.exposeDruid = exposeDruid;
    }

    public boolean isTrustForwardedHeaders() {
        return trustForwardedHeaders;
    }

    public void setTrustForwardedHeaders(boolean trustForwardedHeaders) {
        this.trustForwardedHeaders = trustForwardedHeaders;
    }

    public List<String> getCorsAllowedOrigins() {
        return corsAllowedOrigins;
    }

    public void setCorsAllowedOrigins(List<String> corsAllowedOrigins) {
        this.corsAllowedOrigins = corsAllowedOrigins == null ? new ArrayList<>() : corsAllowedOrigins;
    }

    public boolean isCookieCsrfEnabled() {
        return cookieCsrfEnabled;
    }

    public void setCookieCsrfEnabled(boolean cookieCsrfEnabled) {
        this.cookieCsrfEnabled = cookieCsrfEnabled;
    }

    public long getHstsMaxAgeSeconds() {
        return hstsMaxAgeSeconds;
    }

    public void setHstsMaxAgeSeconds(long hstsMaxAgeSeconds) {
        this.hstsMaxAgeSeconds = hstsMaxAgeSeconds;
    }

    public boolean isHstsIncludeSubDomains() {
        return hstsIncludeSubDomains;
    }

    public void setHstsIncludeSubDomains(boolean hstsIncludeSubDomains) {
        this.hstsIncludeSubDomains = hstsIncludeSubDomains;
    }

    public boolean isHstsPreload() {
        return hstsPreload;
    }

    public void setHstsPreload(boolean hstsPreload) {
        this.hstsPreload = hstsPreload;
    }

    public String getContentSecurityPolicy() {
        return contentSecurityPolicy;
    }

    public void setContentSecurityPolicy(String contentSecurityPolicy) {
        this.contentSecurityPolicy = contentSecurityPolicy == null ? "" : contentSecurityPolicy;
    }
}
