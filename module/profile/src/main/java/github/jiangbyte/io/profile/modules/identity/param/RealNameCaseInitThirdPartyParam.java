package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发起第三方实人认证。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseInitThirdPartyParam {

    private String businessType;
    @NotBlank
    private String documentType;
    @NotBlank
    private String realName;
    @NotBlank
    private String documentNo;
    private String provider;
}
