package ${basePackage}.convert;

/**
 * Author: ${author}
 **/

import ${basePackage}.entity.${entityName};
import ${paramPackage}.${entityName}AddParam;
import ${paramPackage}.${entityName}EditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ${entityName}Convert {

    ${entityName} toEntity(${entityName}AddParam param);

    void update(${entityName}EditParam param, @MappingTarget ${entityName} entity);
}
