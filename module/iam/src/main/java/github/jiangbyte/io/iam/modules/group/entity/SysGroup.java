package github.jiangbyte.io.iam.modules.group.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import github.jiangbyte.io.iam.modules.dept.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.Map;

/**
 * 用户组实体，对应表 sys_group。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_group", autoResultMap = true)
public class SysGroup extends BaseEntity {
    private String name;
    @Trans(
            type = TransType.SIMPLE,
            target = SysDept.class,
            fields = "name",
            ref = "ownerDeptIdName")
    private String ownerDeptId;
    private String description;
    private String status;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private String ownerDeptIdName;
}
