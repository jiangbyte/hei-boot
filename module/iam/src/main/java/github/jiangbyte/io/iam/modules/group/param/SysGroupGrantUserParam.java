package github.jiangbyte.io.iam.modules.group.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组成员授权入参（账号 id 列表）。
 *
 * Author: Charlie
 */
@Data
public class SysGroupGrantUserParam {

    @NotBlank
    private String id;
    private List<String> accountIds = new ArrayList<>();
}
