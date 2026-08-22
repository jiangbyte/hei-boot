package github.jiangbyte.io.profile.modules.identity.result;

import lombok.Data;

/**
 * 实名工单附件展示结果。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseAttachmentResult {
    private String objectName;
    private String id;
    private String originalName;
    private String contentType;
    private Long size;
    private String url;
}
