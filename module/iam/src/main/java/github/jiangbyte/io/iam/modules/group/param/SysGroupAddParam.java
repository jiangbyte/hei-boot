package github.jiangbyte.io.iam.modules.group.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 创建用户组入参。
 *
 * Author: Charlie
 */
@Data
public class SysGroupAddParam {

    @NotBlank
    private String name;
    private String ownerDeptId;
    private String description;
    private String status = "ENABLED";
    private Map<String, Object> extra = Map.of();
}
