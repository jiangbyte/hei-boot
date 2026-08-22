package github.jiangbyte.io.workspace.modules.overview.result;

import github.jiangbyte.io.workspace.modules.shortcut.result.WorkspaceShortcutResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作台总览：快捷应用 + 本人近期操作/登录日志。
 *
 * Author: Charlie
 */
@Data
public class WorkspaceOverviewResult {
    private List<WorkspaceShortcutResult> shortcuts = new ArrayList<>();
    private List<WorkspaceActivityItemResult> recentOperations = new ArrayList<>();
    private List<WorkspaceActivityItemResult> recentLogins = new ArrayList<>();
}
