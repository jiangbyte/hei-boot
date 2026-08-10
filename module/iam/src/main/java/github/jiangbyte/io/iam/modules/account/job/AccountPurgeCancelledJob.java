package github.jiangbyte.io.iam.modules.account.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
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

    @XxlJob("accountPurgeCancelledJob")
    /** 执行过期已取消账号清理。 */
    public void execute() {
        int retentionDays = resolveRetentionDays();
        int purged = accountService.purgeExpiredCancelledAccounts(retentionDays);
        XxlJobHelper.log("Purged {} cancelled account(s) with retentionDays={}", purged, retentionDays);
        XxlJobHelper.handleSuccess("purged=" + purged);
    }

    /** 解析保留天数配置。 */
    private int resolveRetentionDays() {
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.hasText(param)) {
            try {
                return Integer.parseInt(param.trim());
            } catch (NumberFormatException ignored) {
                XxlJobHelper.log("Invalid job param '{}', fallback to ACCOUNT_CANCEL_RETENTION_DAYS", param);
            }
        }
        return configApi.getInt("ACCOUNT_CANCEL_RETENTION_DAYS", 15);
    }
}
