package github.jiangbyte.io.workspace.modules.shortcut.result;

import lombok.Data;

/**
 * 工作台个人快捷应用项。
 *
 * Author: Charlie
 */
@Data
public class WorkspaceShortcutResult {
    private String id;
    private String resourceId;
    private Integer sort;
    private String name;
    private String path;
    private String icon;
    private String code;
    private String resourceType;
    private String status;
}
