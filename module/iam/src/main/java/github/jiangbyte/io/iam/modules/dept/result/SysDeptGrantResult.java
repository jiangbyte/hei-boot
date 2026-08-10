package github.jiangbyte.io.iam.modules.dept.result;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 部门授予结果/明细：部门 id、是否主部门、排序等。
 *
 * Author: Charlie
 */
@Data
public class SysDeptGrantResult {

    @NotBlank
    private String deptId;
    private Boolean isPrimary = false;
}
