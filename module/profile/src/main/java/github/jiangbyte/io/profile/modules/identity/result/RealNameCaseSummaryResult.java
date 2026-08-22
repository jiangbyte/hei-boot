package github.jiangbyte.io.profile.modules.identity.result;

import github.jiangbyte.io.common.core.jackson.Sensitive;
import github.jiangbyte.io.common.core.jackson.SensitiveStrategy;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 实名业务工单摘要（列表/状态联查）。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseSummaryResult {

    private String caseId;
    private String accountId;
    private String businessType;
    private String verifyChannel;
    private String status;
    private String documentType;
    @Sensitive(strategy = SensitiveStrategy.KEEP, keepPrefix = 1, keepSuffix = 0)
    private String realNameMasked;
    @Sensitive(strategy = SensitiveStrategy.KEEP, keepPrefix = 3, keepSuffix = 4)
    private String documentNoMasked;
    private OffsetDateTime createdAt;
    private OffsetDateTime reviewedAt;
    private String rejectReason;
}
