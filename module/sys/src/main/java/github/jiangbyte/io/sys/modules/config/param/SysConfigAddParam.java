package github.jiangbyte.io.sys.modules.config.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 创建系统配置入参。
 *
 * Author: Charlie
 */
@Data
public class SysConfigAddParam {

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
