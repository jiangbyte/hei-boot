package github.jiangbyte.io.common.web.jackson;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * 布尔反序列化器：兼容字符串 "true"/"false"/"1"/"0" 等宽松输入。
 *
 * Author: Charlie
 */
public final class StringlyBooleanDeserializer extends ValueDeserializer<Boolean> {

    public static final StringlyBooleanDeserializer INSTANCE = new StringlyBooleanDeserializer();

    private StringlyBooleanDeserializer() {
    }

    /** 将 JSON 值宽松解析为 Boolean。 */
    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonToken token = p.currentToken();
        if (token == JsonToken.VALUE_STRING) {
            String raw = p.getText();
            if (raw == null) {
                return null;
            }
            String text = raw.trim();
            if (text.isEmpty()) {
                return null;
            }
            if ("true".equalsIgnoreCase(text) || "1".equals(text)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(text) || "0".equals(text)) {
                return Boolean.FALSE;
            }
            return (Boolean) ctxt.handleWeirdStringValue(Boolean.class, text, "expected \"true\" or \"false\"");
        }
        if (token == JsonToken.VALUE_NULL) {
            return null;
        }
        return (Boolean) ctxt.handleUnexpectedToken(Boolean.class, p);
    }

    /** 返回空值时的默认 Boolean。 */
    @Override
    public Boolean getNullValue(DeserializationContext ctxt) {
        return null;
    }
}
