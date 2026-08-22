package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 第三方实人认证回调参数。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseCallbackParam {

    @NotBlank
    private String caseId;
    private String providerOrderNo;
    private Boolean success;
    private String message;
}
