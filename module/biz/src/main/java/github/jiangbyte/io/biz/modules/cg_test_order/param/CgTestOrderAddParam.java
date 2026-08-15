package github.jiangbyte.io.biz.modules.cg_test_order.param;

/**
 * 创建Order入参。
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
public class CgTestOrderAddParam {
    @NotBlank
    private String orderNo;
    @NotBlank
    private String name;
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerPhone;
    @NotBlank
    private String status;
    @NotBlank
    private String type;
    private OffsetDateTime orderedAt;
    private OffsetDateTime paidAt;
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private Integer itemCount;
    @NotNull
    private Boolean needInvoice;
    private java.util.Map<String, Object> invoiceConfig;
    @NotBlank
    private String remark;
    private java.util.Map<String, Object> extra;
}
