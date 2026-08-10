package github.jiangbyte.io.user.portal.profile;

import lombok.Data;

/**
 * 门户端用户档案跨模块快照：姓名、昵称、头像与联系方式等展示字段。
 * 非 HTTP 结果，亦非持久化实体。
 *
 * Author: Charlie
 */
@Data
public class PortalUserProfileInfo {
    private String accountId;
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
    private String phone;
    private String email;
}
