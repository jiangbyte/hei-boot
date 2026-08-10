package github.jiangbyte.io.iam.modules.account.result;

import github.jiangbyte.io.iam.modules.dept.result.SysDeptGrantResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号已拥有部门授权结果（部门授予明细列表）。
 *
 * Author: Charlie
 */
@Data
public class SysAccountOwnDeptResult {

    private String id;
    private List<SysDeptGrantResult> grantInfoList = new ArrayList<>();
}
