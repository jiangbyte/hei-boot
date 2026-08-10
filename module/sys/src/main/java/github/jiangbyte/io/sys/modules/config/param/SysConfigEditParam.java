package github.jiangbyte.io.sys.modules.config.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑系统配置入参。
 *
 * Author: Charlie
 */
@Data
public class SysConfigEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
    @NotBlank
    private String configKey;
    private String configValue;
    private String category;
    private String remark;
    private Integer sortCode = 0;
    private String valueType = "STRING";
    private String label;
    private String scope;
    private String scene;
    private Boolean isBuiltin = false;
    private Map<String, Object> extJson = Map.of();
}
