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
    private String label;
    private String dbType;
    private String valueType;
    private String uiType;
    private Boolean primaryKey;
    private Boolean nullable;
    private Integer maxLength;
}
