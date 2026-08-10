package github.jiangbyte.io.sys.modules.file.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑文件元数据入参。
 *
 * Author: Charlie
 */
@Data
public class SysFileEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
    @NotBlank
    private String originalName;
}
