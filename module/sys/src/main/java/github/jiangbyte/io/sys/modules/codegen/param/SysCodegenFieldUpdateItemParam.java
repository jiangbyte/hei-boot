package github.jiangbyte.io.sys.modules.codegen.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 单条代码生成字段更新项入参。
 *
 * Author: Charlie
 */
@Data
public class SysCodegenFieldUpdateItemParam {
    private String id;

    @NotBlank
    @Size(max = 16)
    private String tableRole = "MAIN";

    @NotBlank
    @Size(max = 128)
    private String columnName;

    private String columnComment;

    @NotBlank
    @Size(max = 128)
    private String dbType;

    @NotBlank
    @Size(max = 64)
    private String dataType = "str";

    @NotBlank
    @Size(max = 64)
    private String frontendType = "string";

    @NotBlank
    @Size(max = 32)
    private String formWidget = "input";

    private String dictCode;
    private String queryOperator;

    @NotNull
    private Boolean showInTable = true;

    @NotNull
    private Boolean showInForm = true;

    @NotNull
    private Boolean showInDetail = true;

    @NotNull
    private Boolean showInQuery = false;

    @NotNull
    private Boolean isPrimaryKey = false;

    @NotNull
    private Boolean isRequired = false;

    @NotNull
    private Boolean isUnique = false;

    @NotNull
    private Boolean isNullable = true;

    private Integer maxLength;

    @NotNull
    private Integer sort = 99;
}
