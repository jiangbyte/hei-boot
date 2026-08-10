package github.jiangbyte.io.iam.modules.resource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 管理端资源模块实体，对应表 sys_resource_module。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_resource_module", autoResultMap = true)
public class SysResourceModule extends CommonEntity {
    private String name;
    private String code;
    private String client;
    private String icon;
    private String color;
    private Integer sort;
    private String status;
    private String description;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;
}
