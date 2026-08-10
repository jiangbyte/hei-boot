package github.jiangbyte.io.message.modules.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(
        value = "msg_notice_read",
        excludeProperty = {"createdAt", "createdBy", "updatedAt", "updatedBy"})
/**
 * 消息已读记录实体，对应表 {@code msg_notice_read}；按账号类型与账号 ID 记录已读时间。
 *
 * Author: Charlie
 */
public class MsgNoticeRead extends BaseEntity {
    private String noticeId;
    private String accountType;
    private String accountId;
    private OffsetDateTime readAt;
}
