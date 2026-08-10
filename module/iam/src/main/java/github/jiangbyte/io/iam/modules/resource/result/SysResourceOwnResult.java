package github.jiangbyte.io.iam.modules.resource.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 主体已拥有资源授权结果（授予明细列表）。
 *
 * Author: Charlie
 */
@Data
public class SysResourceOwnResult {

    private String id;
    private List<SysResourceGrantModuleOptionResult> modules = new ArrayList<>();
    private List<SysResourceGrantResult> grantInfoList = new ArrayList<>();
}
