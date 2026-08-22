package github.jiangbyte.io.workspace.modules.overview.result;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 工作台个人近期日志摘要项。
 *
 * Author: Charlie
 */
@Data
public class WorkspaceActivityItemResult {
    private String id;
    private String module;
    private String moduleLabel;
    private String action;
    private String actionName;
    private String actionType;
    private String summary;
    private Boolean success;
    private String ip;
    private String userAgent;
    private String operatorName;
    private Integer durationMs;
    private String resourceId;
    private OffsetDateTime createdAt;
}
