package github.jiangbyte.io.iam.modules.client.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.JacksonJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客户端资源（菜单/按钮等）实体，对应表 sys_client_resource。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_client_resource", autoResultMap = true)
public class SysClientResource extends BaseEntity {
    @Trans(
            type = TransType.SIMPLE,
            target = SysClientResource.class,
            fields = "name",
            ref = "parentIdName")
    private String parentId;
    private String code;
    private String name;
    private String resourceType;
    @Trans(
            type = TransType.SIMPLE,
            target = SysClientModule.class,
            fields = "name",
            ref = "moduleIdName")
    private String moduleId;
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private String color;
    private String href;
    private Integer sort;
    private Boolean isVisible;
    private Boolean isCache;
    private Boolean isAffix;
    private String status;
    private String description;
    private String layout;
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private String parentIdName;

    @TableField(exist = false)
    private String moduleIdName;

    @TableField(exist = false)
    private String accountType;

    @TableField(exist = false)
    private List<SysClientResource> children = new ArrayList<>();
}
