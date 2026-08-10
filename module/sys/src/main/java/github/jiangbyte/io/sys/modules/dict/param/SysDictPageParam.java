package github.jiangbyte.io.sys.modules.dict.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictPageParam extends PageQuery {

    private String code;
    private String category;
    private String parentId;
    private String status;
}
