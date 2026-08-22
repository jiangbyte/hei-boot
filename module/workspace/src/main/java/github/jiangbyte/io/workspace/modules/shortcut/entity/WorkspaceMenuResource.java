package github.jiangbyte.io.workspace.modules.shortcut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作台菜单资源投影，对应表 sys_resource。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_resource")
public class WorkspaceMenuResource extends BaseEntity {
    private String code;
    private String name;
    private String resourceType;
    private String path;
    private String icon;
    private String status;
}
