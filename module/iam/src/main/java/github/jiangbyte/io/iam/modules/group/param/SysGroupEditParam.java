package github.jiangbyte.io.iam.modules.group.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑用户组入参。
 *
 * Author: Charlie
 */
@Data
public class SysGroupEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String name;
    private String ownerDeptId;
    private String description;
    private String status = "ENABLED";
    private Map<String, Object> extra = Map.of();
}
