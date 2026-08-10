package github.jiangbyte.io.sys.modules.weakpassword.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 弱密码分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysWeakPasswordPageParam extends PageQuery {

    private String password;
    private String keyword;
}
