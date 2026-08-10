package github.jiangbyte.io.message.modules.feedback.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈分页查询参数（状态、分类及分页字段）。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MsgFeedbackPageParam extends PageQuery {

    private String status;
    private String category;
}
