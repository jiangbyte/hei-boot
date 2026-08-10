package github.jiangbyte.io.iam.modules.role.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色成员授权入参（账号 id 列表）。
 *
 * Author: Charlie
 */
@Data
public class SysRoleGrantUserParam {

    @NotBlank
    private String id;
    private List<String> accountIds = new ArrayList<>();
}
