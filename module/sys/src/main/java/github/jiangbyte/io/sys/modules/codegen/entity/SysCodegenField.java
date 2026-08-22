package github.jiangbyte.io.sys.modules.codegen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
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
public class SysCodegenField extends BaseEntity {
    private String planId;
    private String tableRole;
    private String columnName;
    /** 字段展示标签（通常来自库表注释）。 */
    private String label;
    private String dbType;
    /** 语义值类型（如 str / int / bool / datetime / dict）。 */
    private String valueType;
    /** UI 类型（如 string / number / boolean）。 */
    private String uiType;
    private String widget;
    private String dictCode;
    private String queryOperator;
    private Boolean inTable;
    private Boolean inForm;
    private Boolean inDetail;
    private Boolean inQuery;
    private Boolean primaryKey;
    private Boolean required;
    private Boolean uniqueFlag;
    private Boolean nullable;
    private Integer maxLength;
    private Integer sort;
}
