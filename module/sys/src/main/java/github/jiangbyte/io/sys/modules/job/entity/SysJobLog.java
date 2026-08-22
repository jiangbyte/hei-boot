package github.jiangbyte.io.sys.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 任务执行记录实体，对应表 sys_job_log。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job_log", autoResultMap = true)
public class SysJobLog extends BaseEntity {

    /** 任务 ID。 */
    private String jobId;

    /** 执行参数快照（JSON 存储）。 */
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> params;

    /** 执行开始时间。 */
    private OffsetDateTime startedAt;

    /** 执行用时（毫秒）。 */
    private Long durationMs;

    /** 执行结果：是否成功。 */
    private Boolean success;

    /** 执行结果摘要 / 错误信息。 */
    private String result;

    /** 执行人（人工触发为账号 id，调度触发为 system）。 */
    private String executor;

    /** 执行实例 IP。 */
    private String ip;

    /** 执行实例进程 ID。 */
    private String processId;

    /** 执行实例程序目录。 */
    private String appDir;
}
