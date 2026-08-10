package github.jiangbyte.io.iam.modules.resource.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理端资源绑定权限入参。
 *
 * Author: Charlie
 */
@Data
public class SysResourcePermissionBindParam {

    @NotBlank
    private String resourceId;
    @NotBlank
    private String permissionKey;
    private String accountType = "ADMIN";
    private String dataScope = "SELF";
    private List<String> customScopeDeptIds = new ArrayList<>();
    private Integer sort = 99;
    private String description;
}
