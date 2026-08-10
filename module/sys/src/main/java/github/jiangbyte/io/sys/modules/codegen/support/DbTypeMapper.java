package github.jiangbyte.io.sys.modules.codegen.support;

/**
 * 数据库类型到 Java 类型映射工具。
 *
 * Author: Charlie
 */
public final class DbTypeMapper {

    private DbTypeMapper() {
    }

    public static String[] toPythonAndTs(String dataType, String udtName) {
        String type = (udtName == null ? dataType : udtName).toLowerCase();
        return switch (type) {
            case "int2", "int4", "int8", "integer", "bigint", "smallint", "serial", "bigserial" ->
                    new String[]{"int", "number"};
            case "numeric", "decimal", "float4", "float8", "double precision", "real", "money" ->
                    new String[]{"float", "number"};
            case "bool", "boolean" -> new String[]{"bool", "boolean"};
            case "json", "jsonb" -> new String[]{"dict", "Record<string, any>"};
            case "timestamp", "timestamptz", "timestamp without time zone", "timestamp with time zone",
                 "date", "time", "timetz" -> new String[]{"datetime", "string"};
            default -> new String[]{"str", "string"};
        };
    }

    public static String toJavaType(String pythonType) {
        if (pythonType == null) {
            return "String";
        }
        return switch (pythonType) {
            case "int" -> "Integer";
            case "float" -> "java.math.BigDecimal";
            case "bool" -> "Boolean";
            case "datetime" -> "java.time.OffsetDateTime";
            case "dict" -> "java.util.Map<String, Object>";
            default -> "String";
        };
    }

    public static String toSimpleJavaName(String javaType) {
        if (javaType == null) {
            return "String";
        }
        int idx = javaType.lastIndexOf('.');
        if (idx < 0) {
            return javaType;
        }
        // 保留泛型，如 Map<String, Object>
        if (javaType.contains("<")) {
            return javaType;
        }
        return javaType.substring(idx + 1);
    }
}
