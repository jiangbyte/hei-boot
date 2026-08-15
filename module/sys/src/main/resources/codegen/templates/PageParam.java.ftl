package ${paramPackage};

<#if businessName?? && businessName?has_content>
/**
 * ${businessName}分页查询入参。
 *
 * Author: ${author}
 */
<#else>
/**
 * 分页查询入参。
 *
 * Author: ${author}
 */
</#if>

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
