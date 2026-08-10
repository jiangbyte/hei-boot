package github.jiangbyte.io.iam.modules.account.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号授权角色入参：账号 id + 角色 id 列表。
 *
 * Author: Charlie
 */
@Data
public class SysAccountGrantRoleParam {

    @NotBlank
    private String id;
    private List<String> roleIds = new ArrayList<>();
}
