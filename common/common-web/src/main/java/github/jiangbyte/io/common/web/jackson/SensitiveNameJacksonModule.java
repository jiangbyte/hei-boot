package github.jiangbyte.io.common.web.jackson;

import github.jiangbyte.io.common.core.jackson.Sensitive;
import github.jiangbyte.io.common.core.sensitive.SensitiveKeys;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;

import java.util.List;
import java.util.Set;

/**
 * 按 {@link Sensitive} 注解或属性名自动脱敏（Jackson 3：ValueSerializerModifier）。
 *
 * Author: Charlie
 */
public final class SensitiveNameJacksonModule extends SimpleModule {

    public static final String MODULE_NAME = "hei-sensitive-name";

    public SensitiveNameJacksonModule(Set<String> redactKeys) {
        super(MODULE_NAME);
        Set<String> keys = SensitiveKeys.normalize(redactKeys);
        setSerializerModifier(new ValueSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(
                    SerializationConfig config,
                    BeanDescription.Supplier beanDesc,
                    List<BeanPropertyWriter> beanProperties) {
                for (BeanPropertyWriter writer : beanProperties) {
                    if (shouldMask(writer, keys)) {
                        ValueSerializer<Object> ser = new SensitiveJsonSerializer();
                        writer.assignSerializer(ser);
                    }
                }
                return beanProperties;
            }
        });
    }

    private static boolean shouldMask(BeanPropertyWriter writer, Set<String> keys) {
        AnnotatedMember member = writer.getMember();
        if (member != null && member.hasAnnotation(Sensitive.class)) {
            return true;
        }
        return SensitiveKeys.matches(writer.getName(), keys);
    }
}
