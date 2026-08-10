package github.jiangbyte.io.auth.modules.session.result;

import lombok.Data;

/**
 * 单个 Token 会话详情：登录时间、过期时间、客户端 IP 与设备信息。
 *
 * Author: Charlie
 */
@Data
public class SessionTokenResult {
    private String token;
    private String accountId;
    private String accountType;
    private String loginAt;
    private String lastActiveAt;
    private String expiresAt;
    private String clientIp;
    private String deviceLabel;
    private String userAgent;
    private Boolean rememberMe;
}
