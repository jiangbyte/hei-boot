package github.jiangbyte.io.auth.modules.session.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 单个在线账号的会话汇总：账号资料、最近登录信息及下属 Token 列表。
 *
 * Author: Charlie
 */
@Data
public class SessionAccountResult {
    private String accountId;
    private String account;
    private String accountType;
    private String name;
    private String nickname;
    private String avatar;
    private String latestLoginIp;
    private String latestLoginTime;
    private Integer tokenCount;
    private String firstLoginAt;
    private String latestActiveAt;
    private String clientIp;
    private String deviceLabel;
    private List<SessionTokenResult> tokens = new ArrayList<>();
}
