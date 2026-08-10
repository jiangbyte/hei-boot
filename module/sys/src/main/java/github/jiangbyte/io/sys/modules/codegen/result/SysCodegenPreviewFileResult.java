package github.jiangbyte.io.sys.modules.codegen.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码生成预览单文件结果。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysCodegenPreviewFileResult {
    private String path;
    private String language;
    private String content;
}
