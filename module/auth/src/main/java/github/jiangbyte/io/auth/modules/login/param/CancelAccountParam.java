package github.jiangbyte.io.auth.modules.login.param;

import lombok.Data;

/**
 * 注销当前登录账号的请求参数，可选填写注销原因。
 *
 * Author: Charlie
 */
@Data
public class CancelAccountParam {
    private String cancelReason;
}
