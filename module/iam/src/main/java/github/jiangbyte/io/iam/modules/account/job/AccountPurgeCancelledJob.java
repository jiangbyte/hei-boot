package github.jiangbyte.io.iam.modules.account.job;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.common.log.SnailJobLog;
import com.aizuda.snailjob.model.dto.ExecuteResult;
import github.jiangbyte.io.iam.modules.account.service.AccountService;
import github.jiangbyte.io.sys.config.ConfigApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;

/**
 * 定时任务：清理已取消且超过保留期的账号数据（物理清理）。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class AccountPurgeCancelledJob {

    private final AccountService accountService;
    private final ConfigApi configApi;

    @JobExecutor(name = "accountPurgeCancelledJob")
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        int retentionDays = resolveRetentionDays(jobArgs);
        int purged = accountService.purgeExpiredCancelledAccounts(retentionDays);
        SnailJobLog.REMOTE.info("Purged {} cancelled account(s) with retentionDays={}", purged, retentionDays);
        return ExecuteResult.success("purged=" + purged);
    }

    private int resolveRetentionDays(JobArgs jobArgs) {
        Object param = jobArgs == null ? null : jobArgs.getJobParams();
        if (param != null && StringUtils.hasText(String.valueOf(param))) {
            try {
                return Integer.parseInt(String.valueOf(param).trim());
            } catch (NumberFormatException ignored) {
                SnailJobLog.REMOTE.info("Invalid job param '{}', fallback to ACCOUNT_CANCEL_RETENTION_DAYS", param);
            }
        }
        return configApi.getInt("ACCOUNT_CANCEL_RETENTION_DAYS", 15);
    }
}
