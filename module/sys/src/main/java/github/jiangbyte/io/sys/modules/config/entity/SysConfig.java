package github.jiangbyte.io.sys.modules.config.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 系统配置实体，对应表 sys_config。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_config", autoResultMap = true)
public class SysConfig extends BaseEntity {
    private String configKey;
    private String configValue;
    private String category;
    private String remark;
    private Integer sortCode;
    private String valueType;
    private String label;
    private String scope;
    private String scene;
    private Boolean isBuiltin;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> extJson;
}
