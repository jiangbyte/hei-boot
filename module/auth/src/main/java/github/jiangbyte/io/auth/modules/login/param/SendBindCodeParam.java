package github.jiangbyte.io.auth.modules.login.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发送绑定邮箱/手机 OTP 的请求（登录态）。
 *
 * Author: Charlie
 */
@Data
public class SendBindCodeParam {

    @NotBlank
    private String target;
}
