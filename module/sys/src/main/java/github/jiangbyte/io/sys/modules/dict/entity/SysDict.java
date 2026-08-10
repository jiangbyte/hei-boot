package github.jiangbyte.io.sys.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典实体，对应表 sys_dict。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends BaseEntity {
    private String code;
    private String label;
    private String value;
    private String color;
    private String category;
    @Trans(
            type = TransType.SIMPLE,
            target = SysDict.class,
            fields = "label",
            ref = "parentIdName")
    private String parentId;
    private String status;
    private Integer sort;

    @TableField(exist = false)
    private String parentIdName;

    @TableField(exist = false)
    private List<SysDict> children = new ArrayList<>();
}
