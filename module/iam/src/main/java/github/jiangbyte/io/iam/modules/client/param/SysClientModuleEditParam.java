package github.jiangbyte.io.iam.modules.client.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑客户端模块入参。
 *
 * Author: Charlie
 */
@Data
public class SysClientModuleEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String name;
    @NotBlank
    private String code;
    private String accountType = "ADMIN";
    private String icon;
    private String color;
    private Integer sort = 99;
    private String status = "ENABLED";
    private String description;
    private Map<String, Object> extra = Map.of();
}
