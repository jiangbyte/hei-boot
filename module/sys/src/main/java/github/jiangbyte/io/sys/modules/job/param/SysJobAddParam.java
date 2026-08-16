package github.jiangbyte.io.sys.modules.job.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 创建任务入参。
 *
 * Author: Charlie
 */
@Data
public class SysJobAddParam {

    @NotBlank
    @Size(max = 128)
    private String jobName;

    /** 执行类全限定名（容器中实现 JobHandler 的 Bean 类名）。 */
    @NotBlank
    @Size(max = 255)
    private String executeClass;

    /** 触发类型：CRON（表达式）/ FIXED（固定间隔）。 */
    @NotBlank
    @Size(max = 16)
    private String executeType;

    /** 触发配置：CRON 表达式或固定间隔秒数。 */
    @NotBlank
    @Size(max = 255)
    private String triggerConfig;

    /** 执行参数（JSON）。 */
    private Map<String, Object> executeParam;

    /** 任务描述。 */
    @Size(max = 500)
    private String description;

    private Integer sort = 0;

    private Boolean enabled = true;
}
