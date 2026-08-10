package github.jiangbyte.io.iam.modules.resource.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端资源或模块分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResourcePageParam extends PageQuery {

    private String code;
    private String name;
    private String resourceType;
    private String moduleId;
    private String status;
}
