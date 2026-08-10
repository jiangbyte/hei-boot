package github.jiangbyte.io.sys.modules.codegen.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量更新代码生成字段配置入参。
 *
 * Author: Charlie
 */
@Data
public class SysCodegenFieldsUpdateBatchParam {
    @NotBlank
    private String planId;

    @NotEmpty
    @Valid
    private List<SysCodegenFieldUpdateItemParam> fields;
}
