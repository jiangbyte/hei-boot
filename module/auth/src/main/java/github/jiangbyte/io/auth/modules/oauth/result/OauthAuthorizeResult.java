package github.jiangbyte.io.auth.modules.oauth.result;

import lombok.Data;

/**
 * 发起 OAuth 授权的响应。
 *
 * Author: Charlie
 */
@Data
public class OauthAuthorizeResult {
    private String authorizeUrl;
    private String state;
}
