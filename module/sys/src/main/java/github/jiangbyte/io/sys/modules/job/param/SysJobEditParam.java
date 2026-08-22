package github.jiangbyte.io.sys.modules.job.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑任务入参。
 *
 * Author: Charlie
 */
@Data
public class SysJobEditParam {

    @NotBlank
    private String id;

    @NotBlank
    @Size(max = 128)
    private String name;

    /** 处理器标识（Boot 为 JobHandler 全限定类名，其他栈为注册 key）。 */
    @NotBlank
    @Size(max = 255)
    private String handler;

    /** 触发类型：CRON（表达式）/ FIXED（固定间隔）。 */
    @NotBlank
    @Size(max = 16)
    private String triggerType;

    /** 触发配置：CRON 表达式或固定间隔秒数。 */
    @NotBlank
    @Size(max = 255)
    private String triggerConfig;

    /** 执行参数（JSON）。 */
    private Map<String, Object> params;

    /** 任务描述。 */
    @Size(max = 500)
    private String description;

    private Integer sort = 0;

    private Boolean enabled = true;
}
