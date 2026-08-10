package github.jiangbyte.io.sys.modules.audit.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作审计分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAuditPageParam extends PageQuery {
    private String module;
    private String action;
    private String accountId;
    private Boolean success;
}
