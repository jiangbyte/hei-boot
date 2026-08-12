package github.jiangbyte.io.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import github.jiangbyte.io.common.mybatis.datasource.DataSourceRoutingAspect;
import github.jiangbyte.io.common.mybatis.handler.HeiMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * MyBatis-Plus 自动配置：注册元对象填充、分页与数据源路由等基础设施 Bean。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ComponentScan("github.jiangbyte.io.common.mybatis")
public class MybatisAutoConfiguration {

    /** 注册 MyBatis-Plus 拦截器（含分页）。 */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

    /** 注册元对象自动填充处理器。 */
    @Bean
    public HeiMetaObjectHandler heiMetaObjectHandler() {
        return new HeiMetaObjectHandler();
    }

    /** 注册读写数据源路由切面（不依赖应用根包扫描）。 */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceRoutingAspect dataSourceRoutingAspect() {
        return new DataSourceRoutingAspect();
    }
}
