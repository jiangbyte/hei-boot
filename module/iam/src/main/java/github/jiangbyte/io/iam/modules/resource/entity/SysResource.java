package github.jiangbyte.io.iam.modules.resource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理端资源（菜单/按钮等）实体，对应表 sys_resource。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_resource", autoResultMap = true)
public class SysResource extends BaseEntity {
    @Trans(
            type = TransType.SIMPLE,
            target = SysResource.class,
            fields = "name",
            ref = "parentIdName")
    private String parentId;
    private String code;
    private String name;
    private String resourceType;
    @Trans(
            type = TransType.SIMPLE,
            target = SysResourceModule.class,
            fields = {"name", "client"},
            refs = {"moduleIdName", "moduleClient"})
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
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private String parentIdName;

    @TableField(exist = false)
    private String moduleIdName;

    @TableField(exist = false)
    private String moduleClient;

    /**
     * 仅 tree 接口使用；current 等扁平列表不输出空 children。
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<SysResource> children = new ArrayList<>();
}
