package github.jiangbyte.io.iam.modules.dept.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门实体，对应表 sys_dept；支持父子层级组织。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept", autoResultMap = true)
public class SysDept extends BaseEntity {
    @Trans(
            type = TransType.SIMPLE,
            target = SysDept.class,
            fields = "name",
            ref = "parentIdName")
    private String parentId;
    @Trans(
            type = TransType.RPC,
            targetClassName = "github.jiangbyte.io.user.modules.admin.profile.entity.AdminUserProfile",
            fields = "name",
            ref = "masterName")
    private String masterId;
    @Trans(
            type = TransType.RPC,
            targetClassName = "github.jiangbyte.io.user.modules.admin.profile.entity.AdminUserProfile",
            fields = "name",
            ref = "deputyMasterName")
    private String deputyMasterId;
    private String name;
    private String category;
    private Integer sort;
    private Boolean isVirtual;
    private String status;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private String parentIdName;

    @TableField(exist = false)
    private String masterName;

    @TableField(exist = false)
    private String deputyMasterName;

    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<>();
}
