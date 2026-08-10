package ${paramPackage};

/**
 * Author: ${author}
 **/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
<#list imports as imp>
import ${imp};
</#list>

@Data
public class ${entityName}EditParam {

    @NotBlank
    @Size(max = 64)
    private String id;
<#list formFields as field>
<#if field.javaType == "String" && field.isRequired!false>
    @NotBlank
</#if>
    private ${field.javaType} ${field.propertyName};
</#list>
}
