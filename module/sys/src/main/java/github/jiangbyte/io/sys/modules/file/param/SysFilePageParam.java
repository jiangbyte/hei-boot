package github.jiangbyte.io.sys.modules.file.param;

import github.jiangbyte.io.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件分页查询入参。
 *
 * Author: Charlie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFilePageParam extends PageQuery {

    private String originalName;
    private String objectName;
    private String storageProvider;
    private String contentType;
}
