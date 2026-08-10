package github.jiangbyte.io.iam.modules.account.result;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 账号身份结果 DTO，用于详情中展示登录标识列表。
 *
 * Author: Charlie
 */
@Data
public class AccountIdentityResult {
    private String id;
    private String accountId;
    private String identityType;
    private String identifier;
    private Boolean verified;
    private Boolean isPrimary;
    private String bindStatus;
    private OffsetDateTime createdAt;
    private String createdBy;
    private OffsetDateTime updatedAt;
    private String updatedBy;
}
