package github.jiangbyte.io.sys.modules.codegen.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库表元数据查询结果。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysCodegenDatabaseTableResult {
    private String tableName;
    private String tableComment;
}
