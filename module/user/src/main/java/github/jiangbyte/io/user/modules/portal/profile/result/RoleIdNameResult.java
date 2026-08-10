package github.jiangbyte.io.user.modules.portal.profile.result;

import lombok.Data;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.TransPojo;

/**
 * 角色 ID-名称项；通过 RPC 翻译填充 {@code name}。
 *
 * Author: Charlie
 */
@Data
public class RoleIdNameResult implements TransPojo {
    @Trans(
            type = TransType.RPC,
            targetClassName = "github.jiangbyte.io.iam.modules.role.entity.SysRole",
            fields = "name",
            ref = "name")
    private String id;
    private String name;
}
