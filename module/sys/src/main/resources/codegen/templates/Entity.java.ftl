package ${basePackage}.entity;

<#if businessName?? && businessName?has_content>
/**
 * ${businessName}实体，对应表 {@code ${tableName}}。
 *
 * Author: ${author}
 */
<#else>
/**
 * 实体，对应表 {@code ${tableName}}。
 *
 * Author: ${author}
 */
</#if>

import com.baomidou.mybatisplus.annotation.TableName;
<#if hasJson || (hasTree && !isSubEntity)>
import com.baomidou.mybatisplus.annotation.TableField;
</#if>
<#if hasJson>
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
</#if>
import github.jiangbyte.io.common.core.domain.BaseEntity;
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
public class ${entityName} extends BaseEntity {
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
