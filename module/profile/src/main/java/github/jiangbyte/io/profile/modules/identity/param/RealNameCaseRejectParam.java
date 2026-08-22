package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端驳回实名认证工单。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseRejectParam {

    @NotBlank
    private String caseId;
    @NotBlank
    private String rejectReason;
}
