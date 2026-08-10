package github.jiangbyte.io.sys.modules.file.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 按对象名查询文件入参。
 *
 * Author: Charlie
 */
@Data
public class SysFileObjectNameParam {

    @NotBlank
    private String objectName;
}
