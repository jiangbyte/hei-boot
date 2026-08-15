package github.jiangbyte.io.biz.modules.cg_test_catalog.param;

/**
 * 创建Catalog入参。
 *
 * Author: Charlie
 */

import lombok.Data;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    private Integer sort;
    @NotNull
    private Boolean isVisible;
    @NotBlank
    private String icon;
    @NotBlank
    private String description;
    private java.util.Map<String, Object> extra;
}
