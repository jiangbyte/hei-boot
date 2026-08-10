package github.jiangbyte.io.sys.modules.config.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigPageParam extends PageQuery {

    private String configKey;
    private String category;
}
