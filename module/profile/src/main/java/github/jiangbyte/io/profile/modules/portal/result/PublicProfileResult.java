package github.jiangbyte.io.profile.modules.portal.result;

import lombok.Data;

/**
 * 门户公开资料响应：空间详情可见的账号展示字段。
 *
 * Author: Charlie
 */
@Data
public class PublicProfileResult {
    private String accountId;
    /** 对外展示昵称（不含登录账号兜底）。 */
    private String nickname;
    /** 登录账号（资料页单独展示，如 @account）。 */
    private String account;
    private String avatar;
    private String signature;
}
