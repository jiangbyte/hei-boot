package github.jiangbyte.io.sys.modules.job.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务执行记录分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysJobLogPageParam extends PageQuery {

    /** 任务 ID。 */
    private String jobId;

    /** 执行结果：是否成功。 */
    private Boolean success;
}
