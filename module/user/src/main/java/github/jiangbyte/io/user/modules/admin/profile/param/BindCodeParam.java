package github.jiangbyte.io.user.modules.admin.profile.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端发送绑定 OTP 请求。
 *
 * Author: Charlie
 */
@Data
public class BindCodeParam {
    @NotBlank
    private String target;
}
