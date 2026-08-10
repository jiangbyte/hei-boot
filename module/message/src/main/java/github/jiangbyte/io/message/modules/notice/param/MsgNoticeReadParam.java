package github.jiangbyte.io.message.modules.notice.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 标记消息已读的请求参数（消息 ID 列表）。
 *
 * Author: Charlie
 */
@Data
public class MsgNoticeReadParam {

    @NotEmpty
    private List<@NotBlank @Size(max = 64) String> ids;
}
