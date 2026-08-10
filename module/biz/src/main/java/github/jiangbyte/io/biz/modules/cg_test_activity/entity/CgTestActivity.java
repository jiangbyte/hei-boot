package github.jiangbyte.io.biz.modules.cg_test_activity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 测试活动实体，对应表 {@code cg_test_activity}；含编码、分类、时间窗口、名额价格与规则配置等。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_activity", autoResultMap = true)
public class CgTestActivity extends CommonEntity {
    private String code;
    private String name;
    private String category;
    private String type;
    private String status;
    private String coverUrl;
    private String description;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    private Integer maxParticipants;
    private BigDecimal price;
    private Boolean isPublic;
    private Boolean needApproval;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> ruleConfig;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> extra;
    private String ownerDeptId;
}
