package github.jiangbyte.io.sys.modules.config.result;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 系统配置查询结果（含解密后展示值）。
 *
 * Author: Charlie
 */
@Data
public class SysConfigResult {

    private String id;
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
    private Map<String, Object> extJson;
    private OffsetDateTime createdAt;
    private String createdBy;
    private OffsetDateTime updatedAt;
    private String updatedBy;
}
