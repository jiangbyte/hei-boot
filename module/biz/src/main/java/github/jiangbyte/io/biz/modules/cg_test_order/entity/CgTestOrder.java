package github.jiangbyte.io.biz.modules.cg_test_order.entity;

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
 * 测试订单实体，对应表 {@code cg_test_order}；含订单号、客户、类型状态与金额等。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "cg_test_order", autoResultMap = true)
public class CgTestOrder extends CommonEntity {
    private String orderNo;
    private String name;
    private String customerName;
    private String customerPhone;
    private String status;
    private String type;
    private OffsetDateTime orderedAt;
    private OffsetDateTime paidAt;
    private BigDecimal totalAmount;
    private Integer itemCount;
    private Boolean needInvoice;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> invoiceConfig;
    private String remark;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private java.util.Map<String, Object> extra;
    private String ownerDeptId;
}
