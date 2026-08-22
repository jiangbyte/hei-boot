package github.jiangbyte.io.profile.modules.identity.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 账号实名认证快照，对应表 {@code profile_identity}。
 *
 * Author: Charlie
 */
@Data
@TableName("profile_identity")
public class ProfileIdentity {

    @TableId(value = "account_id", type = IdType.INPUT)
    private String accountId;
    private String status;
    private String documentType;
    private String realNameCipher;
    private String documentNoCipher;
    private String documentNoHash;
    private String verifyChannel;
    private String provider;
    private String providerOrderNo;
    private OffsetDateTime verifiedAt;
    private String sourceCaseId;
    private OffsetDateTime revokedAt;
    private String revokedBy;
    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
