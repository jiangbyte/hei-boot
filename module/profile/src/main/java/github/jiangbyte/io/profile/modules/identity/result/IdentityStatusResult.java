package github.jiangbyte.io.profile.modules.identity.result;

import github.jiangbyte.io.common.core.jackson.Sensitive;
import github.jiangbyte.io.common.core.jackson.SensitiveStrategy;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 当前账号实名认证快照与进行中工单摘要。
 *
 * Author: Charlie
 */
@Data
public class IdentityStatusResult {

    private String status;
    private String documentType;
    @Sensitive(strategy = SensitiveStrategy.KEEP, keepPrefix = 1, keepSuffix = 0)
    private String realNameMasked;
    @Sensitive(strategy = SensitiveStrategy.KEEP, keepPrefix = 3, keepSuffix = 4)
    private String documentNoMasked;
    private String verifyChannel;
    private String provider;
    private OffsetDateTime verifiedAt;
    private OffsetDateTime revokedAt;
    private RealNameCaseSummaryResult pendingCase;
}
