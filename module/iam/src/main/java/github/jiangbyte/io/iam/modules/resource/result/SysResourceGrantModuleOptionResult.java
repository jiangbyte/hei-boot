package github.jiangbyte.io.iam.modules.resource.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源授权树中的模块选项（含菜单子节点）。
 *
 * Author: Charlie
 */
@Data
public class SysResourceGrantModuleOptionResult {

    private String id;
    private String title;
    private List<SysResourceGrantMenuOptionResult> menu = new ArrayList<>();
}
