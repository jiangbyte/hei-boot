package github.jiangbyte.io.biz.modules.cg_test_activity.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 编辑测试活动的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestActivityEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
    private Integer maxParticipants;
    private BigDecimal price;
    private Boolean isPublic;
    private Boolean needApproval;
    private java.util.Map<String, Object> ruleConfig;
    private java.util.Map<String, Object> extra;
}
