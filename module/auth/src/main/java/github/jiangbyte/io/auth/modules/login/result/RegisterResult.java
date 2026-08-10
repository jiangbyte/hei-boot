package github.jiangbyte.io.auth.modules.login.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;

/**
 * 门户注册成功响应：新账号 ID、登录名与账号类型。
 *
 * Author: Charlie
 */
@Data
public class RegisterResult {
    private String accountId;
    private String account;
    private AccountType accountType;
}
