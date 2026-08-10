package github.jiangbyte.io.sys.modules.codegen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成字段配置实体，对应表 sys_codegen_field。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_codegen_field")
public class SysCodegenField extends CommonEntity {
    private String planId;
    private String tableRole;
    private String columnName;
    private String columnComment;
    private String dbType;
    private String pythonType;
    private String typescriptType;
    private String formWidget;
    private String dictCode;
    private String queryOperator;
    private Boolean showInTable;
    private Boolean showInForm;
    private Boolean showInDetail;
    private Boolean showInQuery;
    private Boolean isPrimaryKey;
    private Boolean isRequired;
    private Boolean isUnique;
    private Boolean isNullable;
    private Integer maxLength;
    private Integer sort;
}
