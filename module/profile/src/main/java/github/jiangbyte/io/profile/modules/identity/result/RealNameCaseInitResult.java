package github.jiangbyte.io.profile.modules.identity.result;

import lombok.Data;

/**
 * 第三方实人认证发起结果。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseInitResult {

    private String caseId;
    private String provider;
    private String providerOrderNo;
    private String redirectUrl;
}
