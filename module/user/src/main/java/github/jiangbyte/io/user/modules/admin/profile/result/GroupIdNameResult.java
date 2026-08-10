package github.jiangbyte.io.user.modules.admin.profile.result;

import lombok.Data;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.TransPojo;

/**
 * 用户组 ID-名称项；通过 RPC 翻译填充 {@code name}。
 *
 * Author: Charlie
 */
@Data
public class GroupIdNameResult implements TransPojo {
    @Trans(
            type = TransType.RPC,
            targetClassName = "github.jiangbyte.io.iam.modules.group.entity.SysGroup",
            fields = "name",
            ref = "name")
    private String id;
    private String name;
}
