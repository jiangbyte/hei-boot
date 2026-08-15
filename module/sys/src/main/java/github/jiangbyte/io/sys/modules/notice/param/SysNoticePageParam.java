package github.jiangbyte.io.sys.modules.notice.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告/通知分页查询参数（标题、状态、类型及分页字段）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNoticePageParam extends PageQuery {

    private String title;
    private String status;
    private String kind;
}
