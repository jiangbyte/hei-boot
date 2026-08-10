package github.jiangbyte.io.iam.modules.resource.param;

import lombok.Data;

/**
 * 管理端资源树查询入参。
 *
 * Author: Charlie
 */
@Data
public class SysResourceTreeParam {

    private String moduleId;
    private String moduleClient;
}
