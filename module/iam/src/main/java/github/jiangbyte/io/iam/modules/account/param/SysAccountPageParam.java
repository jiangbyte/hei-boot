package github.jiangbyte.io.iam.modules.account.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号分页查询入参（关键字、状态、账号类型等过滤条件）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAccountPageParam extends PageQuery {

    private String account;
    private String name;
    private String phone;
    private String email;
    private String accountType;
    private String accountStatus;
}
