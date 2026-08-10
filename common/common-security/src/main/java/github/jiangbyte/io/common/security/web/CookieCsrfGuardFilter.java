package github.jiangbyte.io.common.security.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

/**
 * 可选 CSRF 守卫：仅在 Cookie 会话且开启 cookie-csrf 时启用。
 * 对携带会话 Cookie 的变更类 /api/** 要求 X-Requested-With 或 X-HEI-CSRF；默认关闭以兼容 fastapi Web。
 *
 * Author: Charlie
 */
public class CookieCsrfGuardFilter extends OncePerRequestFilter {

    public static final String CSRF_HEADER = "X-Requested-With";
    public static final String CSRF_HEADER_ALT = "X-HEI-CSRF";
    /**
     * 与 sa-token.token-name 对齐
     */
    public static final String TOKEN_COOKIE = "Authorization";

    private static final Set<String> SAFE = Set.of(
            HttpMethod.GET.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.TRACE.name());

    private final boolean cookieAuthEnabled;

    public CookieCsrfGuardFilter(boolean cookieAuthEnabled) {
        this.cookieAuthEnabled = cookieAuthEnabled;
    }

    /** 对写操作校验 CSRF 相关请求头。 */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!cookieAuthEnabled || !requiresGuard(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (hasCsrfHeader(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":\"403\",\"message\":\"CSRF guard: missing X-Requested-With\",\"data\":null}");
    }

    private boolean requiresGuard(HttpServletRequest request) {
        String method = request.getMethod();
        if (method == null || SAFE.contains(method.toUpperCase(Locale.ROOT))) {
            return false;
        }
        String path = request.getRequestURI();
        if (path == null || !path.startsWith("/api/")) {
            return false;
        }
        return hasAuthCookie(request);
    }

    private static boolean hasAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (TOKEN_COOKIE.equals(cookie.getName()) && StringUtils.hasText(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCsrfHeader(HttpServletRequest request) {
        String requestedWith = request.getHeader(CSRF_HEADER);
        if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
            return true;
        }
        return "1".equals(request.getHeader(CSRF_HEADER_ALT));
    }
}
