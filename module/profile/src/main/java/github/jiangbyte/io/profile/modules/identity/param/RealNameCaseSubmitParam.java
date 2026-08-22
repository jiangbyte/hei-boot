package github.jiangbyte.io.profile.modules.identity.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交实名认证工单（人工通道）。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseSubmitParam {

    private String businessType;
    @NotBlank
    private String documentType;
    @NotBlank
    private String realName;
    @NotBlank
    private String documentNo;
    private List<String> attachmentIds = new ArrayList<>();
    private String applicantContact;
}
