package github.jiangbyte.io.profile.modules.identity.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端已认证实名快照分页参数。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityPageParam extends PageQuery {
    private String status;
    private String accountId;
    private String documentType;
}
