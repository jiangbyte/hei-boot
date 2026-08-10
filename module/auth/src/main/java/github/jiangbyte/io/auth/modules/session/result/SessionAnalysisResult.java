package github.jiangbyte.io.auth.modules.session.result;

import lombok.Data;

/**
 * 在线会话分析统计：账号/Token 数量、管理端与门户分布、近一小时新增等。
 *
 * Author: Charlie
 */
@Data
public class SessionAnalysisResult {

    private int onlineAccountCount;
    private int onlineTokenCount;
    private int adminAccountCount;
    private int portalAccountCount;
    private int oneHourNewCount;
    private int maxTokenCount;
}
