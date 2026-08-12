package github.jiangbyte.io.auth.modules.oauth.result;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 当前用户三方绑定列表项。
 *
 * Author: Charlie
 */
@Data
public class OauthBindingResult {
    private String provider;
    private String label;
    private String openIdMasked;
    private String nickname;
    private String avatar;
    private OffsetDateTime boundAt;
}
