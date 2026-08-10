package github.jiangbyte.io.biz.modules.cg_test_order.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 编辑测试订单的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestOrderEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
    private BigDecimal totalAmount;
    private Integer itemCount;
    private Boolean needInvoice;
    private java.util.Map<String, Object> invoiceConfig;
    @NotBlank
    private String remark;
    private java.util.Map<String, Object> extra;
}
