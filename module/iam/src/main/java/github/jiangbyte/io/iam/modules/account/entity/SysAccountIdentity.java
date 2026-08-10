package github.jiangbyte.io.iam.modules.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号身份实体，对应表 sys_account_identity；标识 ACCOUNT/EMAIL/PHONE 等登录标识。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_account_identity")
public class SysAccountIdentity extends CommonEntity {
    private String accountId;
    private String identityType;
    private String identifier;
    private Boolean verified;
    private Boolean isPrimary;
    private String bindStatus;
}
