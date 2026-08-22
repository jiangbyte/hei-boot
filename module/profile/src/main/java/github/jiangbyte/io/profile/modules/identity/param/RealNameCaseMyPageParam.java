package github.jiangbyte.io.profile.modules.identity.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的实名认证工单分页参数。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RealNameCaseMyPageParam extends PageQuery {
    private String businessType;
    private String status;
}
