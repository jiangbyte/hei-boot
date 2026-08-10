package github.jiangbyte.io.iam.modules.account.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号授权用户组入参：账号 id + 用户组 id 列表。
 *
 * Author: Charlie
 */
@Data
public class SysAccountGrantGroupParam {

    @NotBlank
    private String id;
    private List<String> groupIds = new ArrayList<>();
}
