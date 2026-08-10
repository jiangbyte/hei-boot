package github.jiangbyte.io.message.modules.notice.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建公告/通知的请求参数（类型、标题内容、目标范围、发布位置与状态等）。
 *
 * Author: Charlie
 */
@Data
public class MsgNoticeAddParam {

    @NotBlank
    private String kind;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String contentType = "TEXT";
    private String category;
    private String severity = "INFO";
    private String targetScope = "ALL";
    private List<String> targetAccountTypes = new ArrayList<>();
    private List<String> targetAccountIds = new ArrayList<>();
    private List<String> targetDeptIds = new ArrayList<>();
    private List<String> targetRoleIds = new ArrayList<>();
    private Map<String, Object> publishLocations = new HashMap<>();
    private Boolean isPinned = false;
    private OffsetDateTime pinnedUntil;
    private String sourceType;
    private String sourceId;
    private String status;
    private OffsetDateTime publishAt;
    private OffsetDateTime expireAt;
    private Map<String, Object> extra = Map.of();
}
