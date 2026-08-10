package github.jiangbyte.io.sys.modules.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.jiangbyte.io.common.core.domain.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件元数据实体，对应表 sys_file。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file")
public class SysFile extends CommonEntity {
    private String objectName;
    private String originalName;
    private String storageProvider;
    private String bucket;
    private String contentType;
    private Long size;
    private String url;
}
