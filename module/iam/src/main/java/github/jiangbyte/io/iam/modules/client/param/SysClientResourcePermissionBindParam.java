package github.jiangbyte.io.iam.modules.client.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端资源绑定权限入参（权限键、数据范围等）。
 *
 * Author: Charlie
 */
@Data
public class SysClientResourcePermissionBindParam {

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
