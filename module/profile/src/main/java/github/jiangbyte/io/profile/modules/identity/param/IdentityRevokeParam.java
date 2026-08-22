package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端撤销账号实名认证。
 *
 * Author: Charlie
 */
@Data
public class IdentityRevokeParam {

    @NotBlank
    private String accountId;
    private String remark;
}
