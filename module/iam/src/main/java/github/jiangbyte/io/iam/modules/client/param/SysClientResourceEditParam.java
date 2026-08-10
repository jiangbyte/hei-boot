package github.jiangbyte.io.iam.modules.client.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑客户端资源入参。
 *
 * Author: Charlie
 */
@Data
public class SysClientResourceEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String resourceType;
    private String parentId;
    private String moduleId;
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private String color;
    private String href;
    private Integer sort = 99;
    private Boolean isVisible = true;
    private Boolean isCache = false;
    private Boolean isAffix = false;
    private String status = "ENABLED";
    private String description;
    private String layout;
    private Map<String, Object> extra = Map.of();
}
