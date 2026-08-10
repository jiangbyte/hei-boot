package github.jiangbyte.io.iam.modules.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * 系统账号实体，对应表 sys_account；承载登录态、状态、密码哈希与取消注销等字段。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_account")
public class SysAccount extends CommonEntity {
    private String passwordHash;
    private String accountType;
    private String accountStatus;
    private OffsetDateTime cancelledAt;
    private String cancelledBy;
    private String cancelReason;
    private String cancelNotifyEmail;
    private String cancelNotifyPhone;
    private String lastLoginIp;
    private String lastLoginAddress;
    private OffsetDateTime lastLoginTime;
    private String lastLoginDevice;
    private String latestLoginIp;
    private String latestLoginAddress;
    private OffsetDateTime latestLoginTime;
    private String latestLoginDevice;
}
