package github.jiangbyte.io.iam.modules.resource.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建管理端按钮资源入参。
 *
 * Author: Charlie
 */
@Data
public class SysResourceButtonAddParam {

    @NotBlank
    private String parentId;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String permissionKey;
    private String dataScope = "SELF";
    private List<String> customScopeDeptIds = new ArrayList<>();
    private Integer sort = 99;
    private String status = "ENABLED";
    private String description;
}
