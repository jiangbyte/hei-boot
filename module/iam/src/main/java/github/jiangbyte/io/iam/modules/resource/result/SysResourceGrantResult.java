package github.jiangbyte.io.iam.modules.resource.result;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 主体资源授予明细：资源 id、数据范围与自定义部门等。
 *
 * Author: Charlie
 */
@Data
public class SysResourceGrantResult {

    @NotBlank
    private String resourceId;
    private List<String> permissionKeys = new ArrayList<>();
}
