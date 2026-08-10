package github.jiangbyte.io.sys.modules.dict.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 创建数据字典入参。
 *
 * Author: Charlie
 */
@Data
public class SysDictAddParam {

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
