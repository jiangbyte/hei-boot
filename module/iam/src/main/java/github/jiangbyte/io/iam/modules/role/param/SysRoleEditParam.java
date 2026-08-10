package github.jiangbyte.io.iam.modules.role.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑角色入参。
 *
 * Author: Charlie
 */
@Data
public class SysRoleEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String category;
    private String scopeType;
    private String ownerDeptId;
    private Integer sort = 99;
    private String status = "ENABLED";
    private Boolean isBuiltin = false;
    private String description;
    private Map<String, Object> extra = Map.of();
}
