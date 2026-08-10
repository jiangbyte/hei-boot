package github.jiangbyte.io.user.modules.portal.profile.result;

import github.jiangbyte.io.common.core.enums.AccountType;
import lombok.Data;
import org.dromara.core.trans.vo.TransPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 门户端当前用户 me 响应：账号会话摘要、展示信息、组织名称与完整资料。
 *
 * Author: Charlie
 */
@Data
public class MeResult implements TransPojo {
    private String accountId;
    private String account;
    private AccountType accountType;
    private String name;
    private String nickname;
    private String avatar;
    private List<String> roleIds = new ArrayList<>();
    private List<String> deptIds = new ArrayList<>();
    private List<String> groupIds = new ArrayList<>();
    private List<RoleIdNameResult> roleIdNames = new ArrayList<>();
    private List<DeptIdNameResult> deptIdNames = new ArrayList<>();
    private List<GroupIdNameResult> groupIdNames = new ArrayList<>();
    private Set<String> permissionKeys;
    private UserProfileResult profile;
}
