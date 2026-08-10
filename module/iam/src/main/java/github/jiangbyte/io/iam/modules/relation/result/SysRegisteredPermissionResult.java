package github.jiangbyte.io.iam.modules.relation.result;

/**
 * 已注册权限结果：权限键、名称、模块/资源编码与动作。
 *
 * Author: Charlie
 */
public record SysRegisteredPermissionResult(
        String permissionKey,
        String name,
        String moduleCode,
        String resourceCode,
        String action
) {
}
