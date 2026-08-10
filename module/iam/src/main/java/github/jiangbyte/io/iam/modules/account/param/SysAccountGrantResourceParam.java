package github.jiangbyte.io.iam.modules.account.param;

import github.jiangbyte.io.iam.modules.resource.result.SysResourceGrantResult;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号授权资源（管理端或客户端）入参：账号 id + 资源授予明细。
 *
 * Author: Charlie
 */
@Data
public class SysAccountGrantResourceParam {

    @NotBlank
    private String id;
    private List<SysResourceGrantResult> grantInfoList = new ArrayList<>();
}
