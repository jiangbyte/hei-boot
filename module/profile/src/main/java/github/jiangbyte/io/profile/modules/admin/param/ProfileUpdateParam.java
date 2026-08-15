package github.jiangbyte.io.profile.modules.admin.param;

import lombok.Data;

/**
 * 管理端更新个人资料的请求参数（姓名、昵称、头像、签名、备注）。
 *
 * Author: Charlie
 */
@Data
public class ProfileUpdateParam {
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
    private String remark;
}
