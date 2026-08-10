package github.jiangbyte.io.iam.modules.resource.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑管理端资源模块入参。
 *
 * Author: Charlie
 */
@Data
public class SysResourceModuleEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

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
