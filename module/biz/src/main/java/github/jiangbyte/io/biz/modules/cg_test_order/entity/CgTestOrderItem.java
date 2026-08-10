package github.jiangbyte.io.biz.modules.cg_test_order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * 测试订单明细实体，对应表 {@code cg_test_order_item}；归属订单并记录明细项信息。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_order_item", autoResultMap = true)
public class CgTestOrderItem extends BaseEntity {
    private String orderId;
    private String skuCode;
    private String name;
    private String category;
    private String status;
    private Integer quantity;
    private BigDecimal unitPrice;
    private OffsetDateTime shippedAt;
    private Boolean isGift;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> itemConfig;
    private String remark;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> extra;
}
