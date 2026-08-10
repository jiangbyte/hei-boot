package github.jiangbyte.io.iam.modules.resource.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 创建管理端资源模块入参。
 *
 * Author: Charlie
 */
@Data
public class SysResourceModuleAddParam {

    @NotBlank
    private String name;
    @NotBlank
    private String code;
    private String client;
    private String icon;
    private String color;
    private Integer sort = 99;
    private String status = "ENABLED";
    private String description;
    private Map<String, Object> extra = Map.of();
}
