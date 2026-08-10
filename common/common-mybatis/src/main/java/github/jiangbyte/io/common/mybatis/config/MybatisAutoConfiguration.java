package github.jiangbyte.io.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import github.jiangbyte.io.common.mybatis.handler.HeiMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * MyBatis-Plus 自动配置：注册元对象填充、分页等基础设施 Bean。
 *
 * Author: Charlie
 */
@AutoConfiguration
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
}
