package github.jiangbyte.io.common.log.format;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 键值结构化日志格式化器：输出与 fastapi 对齐的可读键值行。
 *
 * Author: Charlie
 */
public class HeiKeyValueStructuredLogFormatter implements StructuredLogFormatter<ILoggingEvent> {

    private static final DateTimeFormatter ISO_UTC =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withZone(ZoneOffset.UTC);

    /** 将日志事件格式化为键值结构化文本。 */
    @Override
    public String format(ILoggingEvent event) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(ISO_UTC.format(Instant.ofEpochMilli(event.getTimeStamp())));
        sb.append(" [").append(event.getLevel().toString().toLowerCase()).append("] ");
        sb.append(event.getFormattedMessage());
        sb.append(" [").append(shortLogger(event.getLoggerName())).append(']');

        Map<String, String> mdc = event.getMDCPropertyMap();
        if (mdc != null) {
            mdc.entrySet().stream()
                    .filter(e -> hasText(e.getValue()))
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> appendKv(sb, e.getKey(), e.getValue()));
        }
        if (event.getKeyValuePairs() != null) {
            event.getKeyValuePairs().forEach(pair -> {
                if (pair != null && hasText(String.valueOf(pair.value))) {
                    appendKv(sb, pair.key, String.valueOf(pair.value));
                }
            });
        }
        IThrowableProxy throwable = event.getThrowableProxy();
        if (throwable != null) {
            sb.append('\n').append(ThrowableProxyUtil.asString(throwable));
        }
        sb.append('\n');
        return sb.toString();
    }

    private static String shortLogger(String name) {
        if (name == null || name.isBlank()) {
            return "-";
        }
        int dot = name.lastIndexOf('.');
        return dot >= 0 && dot < name.length() - 1 ? name.substring(dot + 1) : name;
    }

    private static void appendKv(StringBuilder sb, String key, String value) {
        sb.append(' ').append(key).append('=');
        if (value.indexOf(' ') >= 0 || value.indexOf('=') >= 0) {
            sb.append('\'').append(value.replace("'", "\\'")).append('\'');
        } else {
            sb.append(value);
        }
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank() && !"-".equals(value);
    }
}
