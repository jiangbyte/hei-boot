package github.jiangbyte.io.common.web.config;

import github.jiangbyte.io.common.web.jackson.StringlyTypedJacksonModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * Jackson 字符串友好反序列化自动配置：注册 StringlyTypedJacksonModule。
 *
 * Author: Charlie
 */
@AutoConfiguration
public class JacksonStringlyTypedAutoConfiguration {

    /** 注册字符串友好 Jackson Module。 */
    @Bean
    @Order(0)
    public JsonMapperBuilderCustomizer heiStringlyTypedJacksonCustomizer() {
        return builder -> builder.addModule(new StringlyTypedJacksonModule());
    }
}
