package github.jiangbyte.io.biz.modules.cg_test_order.param;

import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;

/**
 * 创建测试订单明细的请求参数。
 *
 * Author: Charlie
 */
@Data
public class CgTestOrderItemAddParam {
    @NotBlank
    private String orderId;
    @NotBlank
    private String skuCode;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String status;
    private Integer quantity;
    private BigDecimal unitPrice;
    private OffsetDateTime shippedAt;
    private Boolean isGift;
    private java.util.Map<String, Object> itemConfig;
    @NotBlank
    private String remark;
    private java.util.Map<String, Object> extra;
}
