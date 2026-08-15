package github.jiangbyte.io.sys.modules.feedback.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 管理端更新反馈的请求参数（状态与回复内容）。
 *
 * Author: Charlie
 */
@Data
public class SysFeedbackEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
    private String status;
    private String reply;
}
