package github.jiangbyte.io.iam.modules.account.param;

import github.jiangbyte.io.iam.modules.dept.result.SysDeptGrantResult;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号授权部门入参：账号 id + 部门授予列表。
 *
 * Author: Charlie
 */
@Data
public class SysAccountGrantDeptParam {

    @NotBlank
    private String id;
    private List<SysDeptGrantResult> grantInfoList = new ArrayList<>();
}
