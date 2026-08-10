package ${paramPackage};

/**
 * Author: ${author}
 **/

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ${entityName}PageParam extends PageQuery {
<#list queryFields as field>
    private ${field.javaType} ${field.propertyName};
</#list>
}
