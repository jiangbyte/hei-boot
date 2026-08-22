package github.jiangbyte.io.profile.modules.identity.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端实名认证待审分页参数。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RealNameCaseReviewPageParam extends PageQuery {
    private String businessType;
    private String status;
    private String accountId;
}
