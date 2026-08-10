package github.jiangbyte.io.biz.modules.cg_test_order.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 编辑测试订单明细的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestOrderItemEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
