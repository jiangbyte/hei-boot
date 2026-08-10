package github.jiangbyte.io.user.modules.portal.profile.result;

import lombok.Data;

/**
 * 门户公开资料响应：空间详情可见的账号展示字段。
 *
 * Author: Charlie
 */
@Data
public class PublicProfileResult {
    private String accountId;
    private String name;
    private String nickname;
    private String avatar;
    private String signature;
}
