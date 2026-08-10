package github.jiangbyte.io.sys.modules.codegen.result;

import lombok.Data;

/**
 * 数据库列元数据查询结果。
 *
 * Author: Charlie
 */
@Data
public class SysCodegenDatabaseColumnResult {
    private String columnName;
    private String columnComment;
    private String dbType;
    private String pythonType;
    private String typescriptType;
    private Boolean isPrimaryKey;
    private Boolean isNullable;
    private Integer maxLength;
}
