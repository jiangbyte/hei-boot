package github.jiangbyte.io.sys.modules.feedback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.sys.modules.feedback.result.SysFeedbackAttachmentResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户反馈实体，对应表 {@code sys_feedback}；含提交人、状态、回复与附件对象名等。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_feedback", autoResultMap = true)
public class SysFeedback extends BaseEntity {
    private String title;
    private String content;
    private String category;
    private String contact;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private List<String> attachObjectNames;
    private String status;
    private String reply;
    private String repliedBy;
    private OffsetDateTime repliedAt;
    private String submitterAccountType;
    private String submitterAccountId;

    @TableField(exist = false)
    private List<SysFeedbackAttachmentResult> attachments = new ArrayList<>();
    @TableField(exist = false)
    private String submitterAvatar;
    @TableField(exist = false)
    private String submitterNickname;
}
