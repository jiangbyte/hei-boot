package github.jiangbyte.io.profile.modules.identity.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 实名业务工单，对应表 {@code real_name_case}。
 *
 * Author: Charlie
 */
@Data
@TableName(value = "real_name_case", autoResultMap = true)
public class RealNameCase {

    @TableId(value = "case_id", type = IdType.ASSIGN_ID)
    private String caseId;
    private String businessType;
    private String verifyChannel;
    private String status;
    private String accountId;
    private String targetAccountHintCipher;
    private String applicantContactCipher;
    private String documentType;
    private String realNameCipher;
    private String documentNoCipher;
    private String documentNoHash;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private List<String> attachmentIds = new ArrayList<>();
    private String payloadCipher;
    private String handlerDeptId;
    private String provider;
    private String providerOrderNo;
    private String submitterId;
    private String reviewerId;
    private OffsetDateTime reviewedAt;
    private String rejectReason;
    private OffsetDateTime expireAt;
    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
