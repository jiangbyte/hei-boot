package github.jiangbyte.io.sys.modules.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.sys.modules.job.entity.SysJobLog;
import github.jiangbyte.io.sys.modules.job.param.SysJobLogPageParam;

/**
 * 任务执行记录服务接口。
 *
 * Author: Charlie
 */
public interface JobLogService extends IService<SysJobLog> {

    /** 分页查询执行记录。 */
    Page<SysJobLog> page(SysJobLogPageParam param);
}
