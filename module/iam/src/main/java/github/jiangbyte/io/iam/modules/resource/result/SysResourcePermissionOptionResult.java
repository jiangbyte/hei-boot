package github.jiangbyte.io.iam.modules.resource.result;

import lombok.Data;

/**
 * 资源可绑定权限选项。
 *
 * Author: Charlie
 */
@Data
public class SysResourcePermissionOptionResult {

    private String id;
    private String permissionKey;
    private String title;
    private String dataScope;
}
