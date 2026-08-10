package github.jiangbyte.io.sys.modules.dict.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 编辑数据字典入参。
 *
 * Author: Charlie
 */
@Data
public class SysDictEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;
    private String label;
    private String value;
    private String color;
    private String category;
    private String parentId;
    private String status = "ENABLED";
    private Integer sort = 0;
}
