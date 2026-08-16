package github.jiangbyte.io.sys.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 任务定义实体，对应表 sys_job。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job", autoResultMap = true)
public class SysJob extends BaseEntity {

    /** 任务名称。 */
    private String jobName;

    /** 执行类全限定名（容器中实现 JobHandler 的 Bean 类名）。 */
    private String executeClass;

    /** 触发类型：CRON（表达式）/ FIXED（固定间隔）。 */
    private String executeType;

    /** 触发配置：CRON 表达式或固定间隔秒数。 */
    private String triggerConfig;

    /** 执行参数（JSON 存储）。 */
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> executeParam;

    /** 上次执行时间。 */
    private OffsetDateTime lastRunTime;

    /** 下次执行时间。 */
    private OffsetDateTime nextRunTime;

    /** 上次执行结果摘要。 */
    private String lastExecuteResult;

    /** 启用状态。 */
    private Boolean enabled;

    /** 任务描述。 */
    private String description;

    /** 排序。 */
    private Integer sort;
}
