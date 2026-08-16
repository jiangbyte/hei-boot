package github.jiangbyte.io.sys.modules.job.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 任务启停入参。
 *
 * Author: Charlie
 */
@Data
public class SysJobEnabledParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotNull
    private Boolean enabled;
}
