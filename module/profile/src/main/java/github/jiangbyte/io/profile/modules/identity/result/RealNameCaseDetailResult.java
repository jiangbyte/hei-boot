package github.jiangbyte.io.profile.modules.identity.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 实名业务工单详情（含附件）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RealNameCaseDetailResult extends RealNameCaseSummaryResult {

    private String provider;
    private String providerOrderNo;
    private String submitterId;
    private String reviewerId;
    private List<RealNameCaseAttachmentResult> attachments = new ArrayList<>();
}
