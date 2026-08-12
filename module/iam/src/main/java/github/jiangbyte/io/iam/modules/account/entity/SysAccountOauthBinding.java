package github.jiangbyte.io.iam.modules.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * 三方登录绑定实体，对应表 sys_account_oauth_binding。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_account_oauth_binding")
public class SysAccountOauthBinding extends BaseEntity {
    private String accountId;
    private String provider;
    private String openId;
    private String unionId;
    private String nickname;
    private String avatar;
    private String rawProfile;
    private OffsetDateTime boundAt;
}
