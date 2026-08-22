package github.jiangbyte.io.sys.modules.job.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysJobPageParam extends PageQuery {

    /** 任务名称（模糊）。 */
    private String name;

    /** 触发类型：CRON / FIXED。 */
    private String triggerType;

    /** 启用状态。 */
    private Boolean enabled;
}
