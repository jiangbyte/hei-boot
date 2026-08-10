package github.jiangbyte.io.sys.modules.codegen.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成方案分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCodegenPlanPageParam extends PageQuery {
    private String name;
    private String mainTable;
    private String genType;
}
