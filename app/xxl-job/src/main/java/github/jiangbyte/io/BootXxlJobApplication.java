package github.jiangbyte.io;

/** Author: Charlie **/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 本地 XXL-JOB Admin 入口；扫描官方 vendored 的 {@code com.xxl.job.admin}。
 */
@SpringBootApplication(scanBasePackages = "com.xxl.job.admin")
@MapperScan({
        "com.xxl.job.admin.business.mapper",
        "com.xxl.job.admin.framework.mapper"
})
public class BootXxlJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootXxlJobApplication.class, args);
    }
}
