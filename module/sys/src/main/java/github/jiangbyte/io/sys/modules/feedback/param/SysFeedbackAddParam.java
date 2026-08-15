package github.jiangbyte.io.sys.modules.feedback.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 提交反馈的请求参数（标题、分类、内容与附件对象名）。
 *
 * Author: Charlie
 */
@Data
public class SysFeedbackAddParam {

    private String title;
    @NotBlank
    private String content;
    private String category = "GENERAL";
    private String contact;
    private List<String> attachObjectNames = new ArrayList<>();
}
