package github.jiangbyte.io.sys.modules.notice.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 公告置顶请求参数（是否置顶与置顶截止时间）。
 *
 * Author: Charlie
 */
@Data
public class SysNoticePinParam {

    @NotBlank
    @Size(max = 64)
    private String id;
    @NotNull
    private Boolean isPinned;
    private OffsetDateTime pinnedUntil;
}
