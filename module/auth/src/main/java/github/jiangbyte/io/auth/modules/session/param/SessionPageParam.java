package github.jiangbyte.io.auth.modules.session.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 在线会话分页查询参数：可按账号类型、ID、账号名、IP 或关键字过滤。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionPageParam extends PageQuery {
    private String accountType;
    private String accountId;
    private String account;
    private String ip;
    private String keyword;
}
