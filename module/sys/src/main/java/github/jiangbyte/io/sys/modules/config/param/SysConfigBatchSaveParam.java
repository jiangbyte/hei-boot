package github.jiangbyte.io.sys.modules.config.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量保存系统配置入参。
 *
 * Author: Charlie
 */
@Data
public class SysConfigBatchSaveParam {

    @NotEmpty
    @Valid
    private List<SysConfigBatchItemParam> items;
}
