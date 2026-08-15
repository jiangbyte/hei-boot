package github.jiangbyte.io.sys.modules.notice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * 公告/通知实体，对应表 {@code sys_notice}；含类型、目标范围、发布状态、置顶与阅读相关字段。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notice", autoResultMap = true)
public class SysNotice extends BaseEntity {
    private String kind;
    private String title;
    private String content;
    private String contentType;
    private String category;
    private String severity;
    private String targetScope;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private List<String> targetAccountTypes;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private List<String> targetAccountIds;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private List<String> targetDeptIds;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private List<String> targetRoleIds;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> publishLocations;
    private Boolean isPinned;
    private OffsetDateTime pinnedUntil;
    private String senderAccountType;
    private String senderAccountId;
    private String sourceType;
    private String sourceId;
    private String status;
    private OffsetDateTime publishAt;
    private OffsetDateTime revokedAt;
    private OffsetDateTime expireAt;
    private Integer viewCount;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private Boolean isRead;
}
