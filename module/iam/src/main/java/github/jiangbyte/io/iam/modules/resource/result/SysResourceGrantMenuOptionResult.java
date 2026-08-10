package github.jiangbyte.io.iam.modules.resource.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源授权树中的菜单选项节点。
 *
 * Author: Charlie
 */
@Data
public class SysResourceGrantMenuOptionResult {

    private String id;
    private String moduleId;
    private String parentId;
    private String parentIdName;
    private String title;
    private List<SysResourcePermissionOptionResult> button = new ArrayList<>();
}
