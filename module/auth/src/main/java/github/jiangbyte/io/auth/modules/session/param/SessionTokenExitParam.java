package github.jiangbyte.io.auth.modules.session.param;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 按 Token 值批量强制下线的请求参数。
 *
 * Author: Charlie
 */
@Data
public class SessionTokenExitParam {

    @NotEmpty
    private List<String> tokens = new ArrayList<>();
}
