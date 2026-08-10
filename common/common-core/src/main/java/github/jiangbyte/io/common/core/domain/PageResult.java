package github.jiangbyte.io.common.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 通用分页结果：记录列表、总数、页码与总页数。
 *
 * Author: Charlie
 */
@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> records;
    private long total;
    private int current;
    private int size;
    private int pages;

    /**
     * 按记录与分页参数构造，并自动计算总页数。
     *
     * @param records 当前页记录
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页条数
     */
    public PageResult(List<T> records, long total, int current, int size) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
    }

    /**
     * 工厂方法：按记录与分页参数构建分页结果。
     *
     * @param records 当前页记录
     * @param total   总记录数
     * @param current 当前页码
     * @param size    每页条数
     */
    public static <T> PageResult<T> of(List<T> records, long total, int current, int size) {
        return new PageResult<>(records, total, current, size);
    }
}
