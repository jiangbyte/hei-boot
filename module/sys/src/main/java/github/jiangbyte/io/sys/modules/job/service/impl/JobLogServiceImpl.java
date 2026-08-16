package github.jiangbyte.io.sys.modules.job.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.sys.modules.job.entity.SysJobLog;
import github.jiangbyte.io.sys.modules.job.mapper.SysJobLogMapper;
import github.jiangbyte.io.sys.modules.job.param.SysJobLogPageParam;
import github.jiangbyte.io.sys.modules.job.service.JobLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 任务执行记录服务实现。
 *
 * Author: Charlie
 */
@Service
public class JobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements JobLogService {

    @Override
    @ReadDataSource
    public Page<SysJobLog> page(SysJobLogPageParam param) {
        return this.getBaseMapper().selectPage(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<SysJobLog>lambdaQuery()
                        .eq(StringUtils.hasText(param.getJobId()), SysJobLog::getJobId, param.getJobId())
                        .eq(param.getSuccess() != null, SysJobLog::getSuccess, param.getSuccess())
                        .orderByDesc(SysJobLog::getExecuteTime));
    }
}
