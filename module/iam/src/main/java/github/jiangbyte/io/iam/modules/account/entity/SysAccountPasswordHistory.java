package github.jiangbyte.io.iam.modules.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 账号密码历史实体，对应表 sys_account_password_history；用于密码复用校验与审计。
 *
 * Author: Charlie
 */
@Data
@TableName("sys_account_password_history")
public class SysAccountPasswordHistory {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String accountId;
    private String passwordHash;
    private String changedBy;
    private String changeReason;
    private OffsetDateTime createdAt;
}
