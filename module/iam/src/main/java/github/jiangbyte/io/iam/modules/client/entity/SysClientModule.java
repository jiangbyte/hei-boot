package github.jiangbyte.io.iam.modules.client.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 客户端资源模块实体，对应表 sys_client_module。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_client_module", autoResultMap = true)
public class SysClientModule extends BaseEntity {
    private String name;
    private String code;
    private String accountType;
    private String icon;
    private String color;
    private Integer sort;
    private String status;
    private String description;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;
}
