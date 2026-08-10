package github.jiangbyte.io.sys.modules.banner.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Banner 分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysBannerPageParam extends PageQuery {

    private String targetAccountType;
    private String category;
    private String type;
    private String position;
    private String status;
}
