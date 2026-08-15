package github.jiangbyte.io.biz.modules.cg_test_order.param;

/**
 * 编辑订单明细入参。
 *
 * Author: Charlie
 */

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal unitPrice;
    private OffsetDateTime shippedAt;
    @NotNull
    private Boolean isGift;
    private java.util.Map<String, Object> itemConfig;
    @NotBlank
    private String remark;
    private java.util.Map<String, Object> extra;
}
