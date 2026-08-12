package github.jiangbyte.io.common.web.config;

import github.jiangbyte.io.common.web.jackson.SensitiveNameJacksonModule;
import github.jiangbyte.io.common.web.jackson.StringlyTypedJacksonModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson 字符串友好反序列化与敏感字段脱敏自动配置。
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

    /** 按属性名脱敏敏感字段。 */
    @Bean
    @Order(1)
    public JsonMapperBuilderCustomizer heiSensitiveNameJacksonCustomizer(Environment environment) {
        String csv = environment.getProperty(
                "hei.logging.redact-keys",
                "password,secret,token,cryptoKey,crypto-key,accessKey,access-key,privateKey,private-key");
        Set<String> keys = Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        return builder -> builder.addModule(new SensitiveNameJacksonModule(keys));
    }
}
