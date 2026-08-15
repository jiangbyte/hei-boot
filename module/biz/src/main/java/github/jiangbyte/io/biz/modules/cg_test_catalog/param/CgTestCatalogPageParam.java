package github.jiangbyte.io.biz.modules.cg_test_catalog.param;

/**
 * Catalog分页查询入参。
 *
 * Author: Charlie
 */

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestCatalogPageParam extends PageQuery {
    private String code;
    private String name;
    private String category;
    private String status;
}
