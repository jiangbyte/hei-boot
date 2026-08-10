package github.jiangbyte.io.sys.modules.codegen.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 保存代码生成方案入参。
 *
 * Author: Charlie
 */
@Data
public class SysCodegenPlanSaveParam {
    private String id;

    @NotBlank
    @Size(max = 128)
    private String name;

    @NotBlank
    @Size(max = 32)
    private String genType = "TABLE";

    @NotBlank
    @Size(max = 64)
    private String author;

    private String description;

    @NotBlank
    @Size(max = 128)
    private String mainTable;

    @NotBlank
    @Size(max = 128)
    private String mainPk = "id";

    @NotBlank
    @Size(max = 128)
    private String mainEntityName;

    @NotBlank
    @Size(max = 255)
    private String mainModulePath;

    @NotBlank
    @Size(max = 128)
    private String mainBusinessName;

    @NotBlank
    @Size(max = 255)
    private String apiPrefix;

    @NotBlank
    @Size(max = 128)
    private String permissionPrefix;

    private String resourceModuleId;
    private String parentResourceId;

    @NotBlank
    @Size(max = 64)
    private String menuName;

    @NotBlank
    @Size(max = 255)
    private String menuPath;

    @NotBlank
    @Size(max = 255)
    private String componentPath;

    private String icon;

    @NotNull
    private Integer sort = 99;

    private String treeParentField;
    private String treeLabelField;
    private String subTable;
    private String subPk;
    private String subForeignKey;
    private String subEntityName;
    private String subBusinessName;
}
