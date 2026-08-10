package github.jiangbyte.io.sys.modules.weakpassword.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建弱密码入参。
 *
 * Author: Charlie
 */
@Data
public class SysWeakPasswordAddParam {

    @NotBlank
    @Size(max = 255)
    private String password;
}
