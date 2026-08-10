package github.jiangbyte.io.common.core.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 通用分页查询入参：当前页与每页条数（带校验边界）。
 *
 * Author: Charlie
 */
@Data
public class PageQuery {

    @Min(1)
    private int current = 1;

    @Min(1)
    @Max(100)
    private int size = 20;
}
