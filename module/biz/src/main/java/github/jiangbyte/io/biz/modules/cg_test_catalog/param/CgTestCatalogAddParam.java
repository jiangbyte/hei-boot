package github.jiangbyte.io.biz.modules.cg_test_catalog.param;

import lombok.Data;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;

/**
 * 创建测试目录的请求参数。
 *
 * Author: Charlie
 */
@Data
public class CgTestCatalogAddParam {
    @NotBlank
    private String parentId;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private String status;
    private Integer sort;
    private Boolean isVisible;
    @NotBlank
    private String icon;
    @NotBlank
    private String description;
    private java.util.Map<String, Object> extra;
}
