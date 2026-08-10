package github.jiangbyte.io.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用「id + 名称」展示 DTO，用于下拉、关联回显等轻量场景。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNameDTO {
    private String id;
    private String name;
}
