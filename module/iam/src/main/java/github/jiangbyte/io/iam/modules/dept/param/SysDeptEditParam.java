package github.jiangbyte.io.iam.modules.dept.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

/**
 * 编辑部门入参。
 *
 * Author: Charlie
 */
@Data
public class SysDeptEditParam {

    @NotBlank
    @Size(max = 64)
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private String parentId;
    private String masterId;
    private String deputyMasterId;
    private Integer sort = 99;
    private Boolean isVirtual = false;
    private String status = "ENABLED";
    private Map<String, Object> extra = Map.of();
}
