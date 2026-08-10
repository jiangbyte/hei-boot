package github.jiangbyte.io.user.modules.portal.profile.result;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 门户端用户资料 DTO：基础资料字段及手机/邮箱登录开关。
 *
 * Author: Charlie
 */
@Data
public class UserProfileResult {
    private String accountId;
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
    private Boolean phoneLoginEnabled;
    private Boolean emailLoginEnabled;
    private String remark;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
