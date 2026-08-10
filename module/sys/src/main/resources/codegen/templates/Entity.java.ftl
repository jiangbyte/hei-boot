package ${basePackage}.entity;

/**
 * Author: ${author}
 **/

import com.baomidou.mybatisplus.annotation.TableName;
<#if hasJson || (hasTree && !isSubEntity)>
import com.baomidou.mybatisplus.annotation.TableField;
</#if>
<#if hasJson>
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
</#if>
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#list imports as imp>
import ${imp};
</#list>
<#if hasTree && !isSubEntity>
import java.util.ArrayList;
import java.util.List;
</#if>

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(<#if hasJson>value = "${tableName}", autoResultMap = true<#else>"${tableName}"</#if>)
public class ${entityName} extends CommonEntity {
<#list fields as field>
<#if field.isJson>
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
</#if>
    private ${field.javaType} ${field.propertyName};
</#list>
<#if hasTree && !isSubEntity>

    @TableField(exist = false)
    private List<${entityName}> children = new ArrayList<>();
</#if>
}
