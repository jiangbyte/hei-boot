package github.jiangbyte.io.workspace.modules.shortcut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作台个人快捷应用，对应表 sys_workspace_shortcut。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_workspace_shortcut")
public class SysWorkspaceShortcut extends BaseEntity {
    private String accountId;
    private String resourceId;
    private Integer sort;
}
