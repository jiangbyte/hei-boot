package github.jiangbyte.io.iam.modules.client.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端模块分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysClientModulePageParam extends PageQuery {

    private String name;
    private String code;
    private String accountType;
    private String status;
}
