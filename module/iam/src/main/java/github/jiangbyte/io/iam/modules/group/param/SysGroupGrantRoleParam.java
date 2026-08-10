package github.jiangbyte.io.iam.modules.group.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组授权角色入参。
 *
 * Author: Charlie
 */
@Data
public class SysGroupGrantRoleParam {

    @NotBlank
    private String id;
    private String accountType = "ADMIN";
    private List<String> roleIds = new ArrayList<>();
}
