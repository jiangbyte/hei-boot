package github.jiangbyte.io.profile.modules.portal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 门户端用户资料实体，对应表 {@code profile_user_portal}；主键为账号 ID。
 *
 * Author: Charlie
 */
@Data
@TableName("profile_user_portal")
public class ProfileUserPortal {
    @TableId(value = "account_id", type = IdType.INPUT)
    private String accountId;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
