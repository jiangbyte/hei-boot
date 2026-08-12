package github.jiangbyte.io.auth.modules.oauth.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信小程序登录请求。
 *
 * Author: Charlie
 */
@Data
public class WechatMpLoginParam {
    @NotBlank
    private String code;
}
