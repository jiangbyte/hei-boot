package github.jiangbyte.io.biz.modules.cg_test_catalog.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试目录分页查询参数（编码、名称、分类、状态）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CgTestCatalogPageParam extends PageQuery {
    private String code;
    private String name;
    private String category;
    private String status;
}
