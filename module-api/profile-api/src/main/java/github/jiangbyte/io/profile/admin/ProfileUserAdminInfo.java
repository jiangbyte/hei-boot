package github.jiangbyte.io.profile.admin;

import lombok.Data;

/**
 * 管理端用户档案跨模块快照：姓名、昵称、头像、联系方式与备注等展示字段。
 * 非 HTTP 结果，亦非持久化实体。
 *
 * Author: Charlie
 */
@Data
public class ProfileUserAdminInfo {
    private String accountId;
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
    private String remark;
}
