package github.jiangbyte.io.iam.modules.account.result;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 账号详情中的三方绑定行。
 *
 * Author: Charlie
 */
@Data
public class AccountOauthBindingResult {
    private String id;
    private String provider;
    private String openId;
    private String unionId;
    private String nickname;
    private String avatar;
    private OffsetDateTime boundAt;
}
