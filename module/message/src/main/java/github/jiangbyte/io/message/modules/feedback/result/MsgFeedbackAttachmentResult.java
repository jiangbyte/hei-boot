package github.jiangbyte.io.message.modules.feedback.result;

import lombok.Data;

/**
 * 反馈附件展示结果：对象名、文件元数据与可访问 URL。
 *
 * Author: Charlie
 */
@Data
public class MsgFeedbackAttachmentResult {
    private String objectName;
    private String id;
    private String originalName;
    private String contentType;
    private Long size;
    private String url;
}
