package github.jiangbyte.io.sys.modules.audit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.mybatis.handler.PostgresJacksonTypeHandler;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 告警日志实体，对应表 sys_alert_log。
 *
 * Author: Charlie
 */
@Data
@TableName(value = "sys_alert_log", autoResultMap = true)
public class SysAlertLog {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String ruleName;
    private String severity;
    private String summary;
    @TableField(typeHandler = PostgresJacksonTypeHandler.class)
    private Map<String, Object> details;
    private String notifiedVia;
    private OffsetDateTime createdAt;
}
