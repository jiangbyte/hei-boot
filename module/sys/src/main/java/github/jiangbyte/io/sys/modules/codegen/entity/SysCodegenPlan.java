package github.jiangbyte.io.sys.modules.codegen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成方案实体，对应表 sys_codegen_plan。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_codegen_plan")
public class SysCodegenPlan extends BaseEntity {
    private String name;
    private String genType;
    private String author;
    private String description;
    private String mainTable;
    private String mainPk;
    private String mainEntityName;
    private String mainModulePath;
    private String mainBusinessName;
    private String apiPrefix;
    private String permissionPrefix;
    private String resourceModuleId;
    private String parentResourceId;
    private String menuName;
    private String menuPath;
    private String componentPath;
    private String icon;
    private Integer sort;
    private String treeParentField;
    private String treeLabelField;
    private String subTable;
    private String subPk;
    private String subForeignKey;
    private String subEntityName;
    private String subBusinessName;
}
