package github.jiangbyte.io.common.security.config;

import github.jiangbyte.io.common.security.web.CookieCsrfGuardFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * Cookie CSRF 守卫自动配置：在 Cookie 会话模式下注册 CSRF Filter。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(HeiSecurityProperties.class)
public class CookieCsrfAutoConfiguration {

    /** 注册 Cookie CSRF 守卫过滤器。 */
    @Bean
    public FilterRegistrationBean<CookieCsrfGuardFilter> cookieCsrfGuardFilterRegistration(
            Environment environment, HeiSecurityProperties securityProperties) {
        boolean cookieAuth = environment.getProperty("sa-token.is-read-cookie", Boolean.class, false);
        boolean csrfEnabled = securityProperties != null && securityProperties.isCookieCsrfEnabled();
        boolean guardOn = Boolean.TRUE.equals(cookieAuth) && csrfEnabled;
        FilterRegistrationBean<CookieCsrfGuardFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CookieCsrfGuardFilter(guardOn));
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 15);
        return registration;
    }
}
