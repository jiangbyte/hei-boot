package github.jiangbyte.io.sys.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.sys.modules.job.entity.SysJobLog;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.OffsetDateTime;

/**
 * 任务执行记录表 Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

    /**
     * 删除执行时间早于 cutoff 的日志，最多 limit 条（按 execute_time 升序优先删最旧）。
     */
    @DeleteProvider(type = SysJobLogSqlProvider.class, method = "deleteExpired")
    int deleteExpired(@Param("cutoff") OffsetDateTime cutoff, @Param("limit") int limit);
}
