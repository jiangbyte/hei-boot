package github.jiangbyte.io.profile.modules.portal.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 门户端发送绑定 OTP 请求。
 *
 * Author: Charlie
 */
@Data
public class BindCodeParam {
    @NotBlank
    private String target;
}
