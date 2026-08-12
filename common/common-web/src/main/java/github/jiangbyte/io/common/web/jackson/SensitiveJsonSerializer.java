package github.jiangbyte.io.common.web.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * 敏感字段序列化器：输出固定掩码。
 *
 * Author: Charlie
 */
public class SensitiveJsonSerializer extends ValueSerializer<Object> {

    public static final String MASK = "***";

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext ctxt) {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(MASK);
        }
    }
}
