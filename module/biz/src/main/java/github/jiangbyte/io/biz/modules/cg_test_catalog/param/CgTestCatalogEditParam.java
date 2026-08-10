package github.jiangbyte.io.biz.modules.cg_test_catalog.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Map;

/**
 * 编辑测试目录的请求参数（含主键 ID）。
 *
 * Author: Charlie
 */
@Data
public class CgTestCatalogEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
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
