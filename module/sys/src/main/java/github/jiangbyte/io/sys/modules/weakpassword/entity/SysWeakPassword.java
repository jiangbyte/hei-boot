package github.jiangbyte.io.sys.modules.weakpassword.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 弱密码实体，对应表 sys_weak_password。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_weak_password")
public class SysWeakPassword extends BaseEntity {
    private String password;
}
