package github.jiangbyte.io.common.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.common.security.satoken.SessionPermissionProvider;
import github.jiangbyte.io.common.security.web.AccountMdcInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 路由鉴权配置：注册拦截规则、匿名路径与异常处理。
 *
 * Author: Charlie
 */
@AutoConfiguration
@EnableConfigurationProperties(HeiSecurityProperties.class)
public class SaTokenSecurityConfig implements WebMvcConfigurer {

    private final HeiSecurityProperties securityProperties;

    public SaTokenSecurityConfig(HeiSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /** 注册 Sa-Token 权限/角色接口实现。 */
    @Bean
    public StpInterface stpInterface() {
        return new SessionPermissionProvider();
    }

    /** 注册 Sa-Token 鉴权与账号 MDC 拦截器。 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> exclude = new ArrayList<>();
        exclude.add("/favicon.ico");
        if (securityProperties.isExposeDocs()) {
            exclude.add("/swagger-ui/**");
            exclude.add("/v3/api-docs/**");
            exclude.add("/doc.html");
            exclude.add("/webjars/**");
        }
        if (securityProperties.isExposeActuator()) {
            exclude.add("/actuator/**");
        }
        if (securityProperties.isExposeDruid()) {
            exclude.add("/druid/**");
        }
        if (securityProperties.getIgnoreUrls() != null) {
            exclude.addAll(securityProperties.getIgnoreUrls());
        }
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/api/*/admin/**")
                            .notMatch(exclude)
                            .check(r -> StpKit.ADMIN.checkLogin());
                    SaRouter.match("/api/*/portal/**")
                            .notMatch(exclude)
                            .check(r -> StpKit.PORTAL.checkLogin());
                }))
                .addPathPatterns("/**");
        // 在 Sa-Token 之后执行，便于 LoginHelper 解析会话写入 MDC。
        registry.addInterceptor(new AccountMdcInterceptor()).addPathPatterns("/**");
    }
}
