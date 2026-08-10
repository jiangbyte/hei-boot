package github.jiangbyte.io.common.security.web;

import github.jiangbyte.io.common.security.config.HeiSecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 安全响应头过滤器：按配置写入 HSTS、CSP 等头。
 *
 * Author: Charlie
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class SecurityHeadersFilter extends OncePerRequestFilter {

    private final HeiSecurityProperties properties;

    public SecurityHeadersFilter(HeiSecurityProperties properties) {
        this.properties = properties;
    }

    /** 写入 HSTS/CSP 等安全响应头。 */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        response.setHeader("Permissions-Policy", "camera=(), microphone=(), geolocation=()");
        if (StringUtils.hasText(properties.getContentSecurityPolicy())) {
            response.setHeader("Content-Security-Policy", properties.getContentSecurityPolicy());
        }
        long hsts = properties.getHstsMaxAgeSeconds();
        if (hsts > 0) {
            StringBuilder value = new StringBuilder("max-age=").append(hsts);
            if (properties.isHstsIncludeSubDomains()) {
                value.append("; includeSubDomains");
            }
            if (properties.isHstsPreload()) {
                value.append("; preload");
            }
            response.setHeader("Strict-Transport-Security", value.toString());
        }
        filterChain.doFilter(request, response);
    }
}
