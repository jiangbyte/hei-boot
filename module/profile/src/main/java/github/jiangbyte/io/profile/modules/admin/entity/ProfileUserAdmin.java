package github.jiangbyte.io.profile.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 管理端用户资料实体，对应表 {@code profile_user_admin}；主键为账号 ID。
 *
 * Author: Charlie
 */
@Data
@TableName("profile_user_admin")
public class ProfileUserAdmin {
    @TableId(value = "account_id", type = IdType.INPUT)
    private String accountId;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
