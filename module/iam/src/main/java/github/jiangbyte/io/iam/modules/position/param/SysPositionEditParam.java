package github.jiangbyte.io.iam.modules.position.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑岗位入参。
 *
 * Author: Charlie
 */
@Data
public class SysPositionEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String name;
    private String category;
    private String ownerDeptId;
    private Integer sort = 99;
    private Boolean isVirtual = false;
    private String status = "ENABLED";
    private String description;
    private Map<String, Object> extra = Map.of();
}
