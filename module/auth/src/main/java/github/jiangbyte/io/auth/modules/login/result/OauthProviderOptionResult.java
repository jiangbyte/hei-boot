package github.jiangbyte.io.auth.modules.login.result;

import lombok.Data;

/**
 * authOptions 中下发的三方登录入口。
 *
 * Author: Charlie
 */
@Data
public class OauthProviderOptionResult {
    private String provider;
    private String label;
    private Boolean enabled = false;
    /** 是否网页 OAuth（小程序为 false，前端不展示跳转按钮）。 */
    private Boolean webOAuth = true;
}
