package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理端通过实名认证工单。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseApproveParam {

    @NotBlank
    private String caseId;
    private String remark;
}
