/** Author: Charlie */

package github.jiangbyte.io.auth.modules.oauth.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OauthExchangeParam {
    @NotBlank
    private String code;
}
