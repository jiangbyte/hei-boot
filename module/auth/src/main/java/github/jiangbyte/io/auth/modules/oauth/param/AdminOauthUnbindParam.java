package github.jiangbyte.io.auth.modules.oauth.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端强制解绑三方账号。
 *
 * Author: Charlie
 */
@Data
public class AdminOauthUnbindParam {
    @NotBlank
    private String accountId;
    @NotBlank
    private String provider;
}
