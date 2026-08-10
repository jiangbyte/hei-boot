package github.jiangbyte.io.iam.modules.resource.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端按钮资源分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResourceButtonPageParam extends PageQuery {

    @NotBlank
    private String parentId;
    private String code;
    private String name;
    private String status;
}
