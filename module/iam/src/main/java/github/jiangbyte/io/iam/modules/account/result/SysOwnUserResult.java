package github.jiangbyte.io.iam.modules.account.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 主体（角色/用户组）已关联账号结果：账号 id 列表。
 *
 * Author: Charlie
 */
@Data
public class SysOwnUserResult {

    private String id;
    private List<SysAccountResult> users = new ArrayList<>();
    private List<String> accountIds = new ArrayList<>();
}
