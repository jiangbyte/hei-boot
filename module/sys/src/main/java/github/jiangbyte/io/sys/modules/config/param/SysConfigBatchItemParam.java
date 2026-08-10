package github.jiangbyte.io.sys.modules.config.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 批量保存配置单项入参。
 *
 * Author: Charlie
 */
@Data
public class SysConfigBatchItemParam {

    @NotBlank
    private String configKey;
    private String configValue;
    private String category;
    private String remark;
    private String valueType;
    private String label;
    private String scope;
    private String scene;
    private Boolean isBuiltin;
}
