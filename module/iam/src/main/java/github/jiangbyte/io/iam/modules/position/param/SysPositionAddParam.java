package github.jiangbyte.io.iam.modules.position.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 创建岗位入参。
 *
 * Author: Charlie
 */
@Data
public class SysPositionAddParam {

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
