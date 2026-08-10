package github.jiangbyte.io.common.mybatis.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * PostgreSQL JSON/JSONB 与 Jackson 对象互转的 TypeHandler。
 *
 * Author: Charlie
 */
@MappedTypes({Map.class, List.class, Object.class})
public class PostgresJacksonTypeHandler extends JacksonTypeHandler {

    public PostgresJacksonTypeHandler(Class<?> type) {
        super(type);
    }

    public PostgresJacksonTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    /** 将对象序列化为 JSON 写入 PreparedStatement。 */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        PGobject json = new PGobject();
        json.setType("json");
        json.setValue(toJson(parameter));
        ps.setObject(i, json);
    }
}
