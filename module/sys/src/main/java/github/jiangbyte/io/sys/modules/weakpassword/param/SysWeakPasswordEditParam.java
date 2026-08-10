package github.jiangbyte.io.sys.modules.weakpassword.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑弱密码入参。
 *
 * Author: Charlie
 */
@Data
public class SysWeakPasswordEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    @Size(max = 255)
    private String password;
}
