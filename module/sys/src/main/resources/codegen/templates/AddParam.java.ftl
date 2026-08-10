package ${paramPackage};

/**
 * Author: ${author}
 **/

import lombok.Data;
<#list imports as imp>
import ${imp};
</#list>
<#list formFields as field>
<#if field.javaType == "String" && field.isRequired!false>
import jakarta.validation.constraints.NotBlank;
<#break>
</#if>
</#list>

@Data
public class ${entityName}AddParam {
<#list formFields as field>
<#if field.javaType == "String" && field.isRequired!false>
    @NotBlank
</#if>
    private ${field.javaType} ${field.propertyName};
</#list>
}
