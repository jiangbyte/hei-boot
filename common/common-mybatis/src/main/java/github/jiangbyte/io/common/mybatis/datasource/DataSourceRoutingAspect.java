package github.jiangbyte.io.common.mybatis.datasource;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 数据源路由切面：根据 @ReadDataSource / @WriteDataSource 切换动态数据源。
 *
 * Author: Charlie
 */
@Aspect
@Order(-1)
@Component
public class DataSourceRoutingAspect {

    /** 切换到读库数据源。 */
    @Around("@within(github.jiangbyte.io.common.mybatis.datasource.ReadDataSource) || @annotation(github.jiangbyte.io.common.mybatis.datasource.ReadDataSource)")
    public Object routeRead(ProceedingJoinPoint joinPoint) throws Throwable {
        return route(joinPoint, DataSourceNames.SLAVE);
    }

    /** 切换到写库数据源。 */
    @Around("@within(github.jiangbyte.io.common.mybatis.datasource.WriteDataSource) || @annotation(github.jiangbyte.io.common.mybatis.datasource.WriteDataSource)")
    public Object routeWrite(ProceedingJoinPoint joinPoint) throws Throwable {
        return route(joinPoint, DataSourceNames.MASTER);
    }

    private Object route(ProceedingJoinPoint joinPoint, String dataSourceName) throws Throwable {
        DynamicDataSourceContextHolder.push(dataSourceName);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }
}
