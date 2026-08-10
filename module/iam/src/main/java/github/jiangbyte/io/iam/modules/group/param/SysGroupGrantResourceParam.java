package github.jiangbyte.io.iam.modules.group.param;

import github.jiangbyte.io.iam.modules.resource.result.SysResourceGrantResult;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组授权资源（管理端或客户端）入参。
 *
 * Author: Charlie
 */
@Data
public class SysGroupGrantResourceParam {

    @NotBlank
    private String id;
    private String accountType = "ADMIN";
    private List<SysResourceGrantResult> grantInfoList = new ArrayList<>();
}
