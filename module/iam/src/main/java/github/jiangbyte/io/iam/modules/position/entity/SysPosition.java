package github.jiangbyte.io.iam.modules.position.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import github.jiangbyte.io.iam.modules.dept.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.Map;

/**
 * 岗位实体，对应表 sys_position。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_position", autoResultMap = true)
public class SysPosition extends CommonEntity {
    private String name;
    private String category;
    @Trans(
            type = TransType.SIMPLE,
            target = SysDept.class,
            fields = "name",
            ref = "ownerDeptIdName")
    private String ownerDeptId;
    private Integer sort;
    private Boolean isVirtual;
    private String status;
    private String description;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> extra;

    @TableField(exist = false)
    private String ownerDeptIdName;
}
