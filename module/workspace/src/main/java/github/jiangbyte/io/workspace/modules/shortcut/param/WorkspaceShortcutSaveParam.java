package github.jiangbyte.io.workspace.modules.shortcut.param;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存工作台个人快捷应用入参（整体替换）。
 *
 * Author: Charlie
 */
@Data
public class WorkspaceShortcutSaveParam {

    @NotNull
    @Size(max = 16)
    private List<String> resourceIds = new ArrayList<>();
}
