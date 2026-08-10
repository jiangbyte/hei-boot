package github.jiangbyte.io.common.security.config;

import github.jiangbyte.io.common.security.web.SessionCookiePathFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * 会话 Cookie Path 自动配置：注册改写 Cookie Path 的 Filter。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SessionCookiePathAutoConfiguration {

    /** 注册会话 Cookie Path 过滤器。 */
    @Bean
    public FilterRegistrationBean<SessionCookiePathFilter> sessionCookiePathFilterRegistration(
            Environment environment) {
        boolean cookieAuth = environment.getProperty("sa-token.is-read-cookie", Boolean.class, false);
        String cookieName = environment.getProperty("sa-token.token-name", "Authorization");
        FilterRegistrationBean<SessionCookiePathFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SessionCookiePathFilter(Boolean.TRUE.equals(cookieAuth), cookieName));
        registration.addUrlPatterns("/api/*");
        // 早于业务写 Cookie，包装 response
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 5);
        return registration;
    }
}
