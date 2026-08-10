package github.jiangbyte.io.common.log.config;

import github.jiangbyte.io.common.log.format.HeiKeyValueStructuredLogFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 日志环境后处理器：根据 hei.logging 将结构化日志格式与服务上下文字段写入 Environment。
 *
 * Author: Charlie
 */
public class HeiLoggingEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final String PROPERTY_SOURCE = "heiLoggingStructuredFormat";
    private static final String KEY_VALUE_FORMATTER = HeiKeyValueStructuredLogFormatter.class.getName();

    /** 启动早期根据配置调整日志相关 Environment 属性。 */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        boolean json = resolveJson(environment);
        // 仅在用户要求键值控制台时覆盖；JSON 为 application.yml 默认。
        if (json) {
            return;
        }
        String format = KEY_VALUE_FORMATTER;
        Map<String, Object> props = new LinkedHashMap<>();
        props.put("logging.structured.format.console", format);
        props.put("logging.structured.format.file", format);
        props.put("logging.structured.json.context.include", "true");
        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE, props));
        // LoggingSystem 也会读取这些早期系统属性。
        System.setProperty("CONSOLE_LOG_STRUCTURED_FORMAT", format);
        System.setProperty("FILE_LOG_STRUCTURED_FORMAT", format);
    }

    static boolean resolveJson(ConfigurableEnvironment environment) {
        Boolean hei = environment.getProperty("hei.logging.json", Boolean.class);
        if (hei != null) {
            return hei;
        }
        Boolean logJson = environment.getProperty("LOG_JSON", Boolean.class);
        if (logJson != null) {
            return logJson;
        }
        return true;
    }

    /** 返回处理器优先级。 */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 20;
    }
}
