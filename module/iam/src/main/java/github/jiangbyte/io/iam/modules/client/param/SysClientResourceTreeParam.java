package github.jiangbyte.io.iam.modules.client.param;

import lombok.Data;

/**
 * 客户端资源树查询入参（账号类型等过滤）。
 *
 * Author: Charlie
 */
@Data
public class SysClientResourceTreeParam {
    private String moduleId;
    private String accountType;
}
