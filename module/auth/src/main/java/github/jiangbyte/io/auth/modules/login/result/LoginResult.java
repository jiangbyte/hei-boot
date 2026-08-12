package github.jiangbyte.io.auth.modules.login.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;

/**
 * 登录/刷新会话成功后的响应：Token、账号信息、密码过期与 TTL。
 *
 * Author: Charlie
 */
@Data
public class LoginResult {
    private String token;
    private String accountId;
    private AccountType accountType;
    private Boolean passwordExpired = false;
    /** 是否需强制绑定邮箱（硬拦截） */
    private Boolean forceBindEmail = false;
    /** 是否需强制绑定手机（硬拦截） */
    private Boolean forceBindPhone = false;
    /**
     * 处于告警窗口时的剩余天数；不适用时为 null。
     */
    private Integer passwordExpiryWarningDays;
    /** 登录/刷新后的绝对 token TTL（秒）；使用 Sa-Token 默认时为 null。 */
    private Long expiresIn;
}
