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

    private String label;

    @NotBlank
    @Size(max = 128)
    private String dbType;

    @NotBlank
    @Size(max = 64)
    private String valueType = "str";

    @NotBlank
    @Size(max = 64)
    private String uiType = "string";

    @NotBlank
    @Size(max = 32)
    private String widget = "input";

    private String dictCode;
    private String queryOperator;

    @NotNull
    private Boolean inTable = true;

    @NotNull
    private Boolean inForm = true;

    @NotNull
    private Boolean inDetail = true;

    @NotNull
    private Boolean inQuery = false;

    @NotNull
    private Boolean primaryKey = false;

    @NotNull
    private Boolean required = false;

    @NotNull
    private Boolean uniqueFlag = false;

    @NotNull
    private Boolean nullable = true;

    private Integer maxLength;

    @NotNull
    private Integer sort = 99;
}
