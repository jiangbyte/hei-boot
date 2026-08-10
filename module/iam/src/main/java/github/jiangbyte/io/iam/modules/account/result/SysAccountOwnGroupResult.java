package github.jiangbyte.io.iam.modules.account.result;

import github.jiangbyte.io.iam.modules.group.entity.SysGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号已拥有用户组结果（用户组 id 列表）。
 *
 * Author: Charlie
 */
@Data
public class SysAccountOwnGroupResult {

    private String id;
    private List<SysGroup> groups = new ArrayList<>();
    private List<String> groupIds = new ArrayList<>();
}
