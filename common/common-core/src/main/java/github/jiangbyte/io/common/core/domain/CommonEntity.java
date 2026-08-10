package github.jiangbyte.io.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.dromara.core.trans.vo.TransPojo;

import java.time.OffsetDateTime;

/**
 * 默认业务实体基类（单继承）。
 * 提供通用审计字段与 MyBatis-Plus 雪花 id，无软删除标记。
 * 表缺少完整审计列时，可在 {@code @TableName(excludeProperty = ...)} 中排除多余字段。
 *
 * Author: Charlie
 */
@Data
public abstract class CommonEntity implements TransPojo {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

}
