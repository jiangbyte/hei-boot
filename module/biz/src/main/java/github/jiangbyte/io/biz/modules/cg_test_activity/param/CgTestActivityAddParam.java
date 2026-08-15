package github.jiangbyte.io.biz.modules.cg_test_activity.param;

/**
 * 创建Activity入参。
 *
 * Author: Charlie
 */

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CgTestActivityAddParam {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    @NotBlank
    private String coverUrl;
    @NotBlank
    private String description;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    @NotNull
    private Integer maxParticipants;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Boolean isPublic;
    @NotNull
    private Boolean needApproval;
    private java.util.Map<String, Object> ruleConfig;
    private java.util.Map<String, Object> extra;
}
