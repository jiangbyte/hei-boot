package github.jiangbyte.io.iam.modules.client.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端资源分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysClientResourcePageParam extends PageQuery {

    private String code;
    private String name;
    private String resourceType;
    private String moduleId;
    private String parentId;
    private String status;
}
