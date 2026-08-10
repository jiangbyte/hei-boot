package github.jiangbyte.io.message.modules.notice.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编辑公告/通知的请求参数（在新增字段基础上携带主键 ID）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MsgNoticeEditParam extends MsgNoticeAddParam {

    @NotBlank
    @Size(max = 64)
    private String id;
}
