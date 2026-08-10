package github.jiangbyte.io.sys.modules.file.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件访问 URL 结果。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysFileUrlResult {

    private String objectName;
    private String url;
}
