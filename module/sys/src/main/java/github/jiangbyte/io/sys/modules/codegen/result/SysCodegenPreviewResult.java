package github.jiangbyte.io.sys.modules.codegen.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成预览结果（多文件）。
 *
 * Author: Charlie
 */
@Data
public class SysCodegenPreviewResult {
    private List<SysCodegenPreviewFileResult> files = new ArrayList<>();
}
