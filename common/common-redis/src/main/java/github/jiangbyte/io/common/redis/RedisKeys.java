package github.jiangbyte.io.common.redis;

import org.springframework.util.StringUtils;

/**
 * Redis 键名约定与拼接工具，统一前缀避免模块冲突。
 *
 * Author: Charlie
 */
public final class RedisKeys {

    private final String prefix;

    public RedisKeys(String prefix) {
        this.prefix = normalize(prefix);
    }

    /** 按片段拼接 Redis 键。 */
    public String of(String... parts) {
        if (parts == null || parts.length == 0) {
            return prefix;
        }
        StringBuilder sb = new StringBuilder(prefix);
        for (String part : parts) {
            if (!StringUtils.hasText(part)) {
                continue;
            }
            sb.append(':').append(part.trim());
        }
        return sb.toString();
    }

    /** 返回业务键统一前缀。 */
    public String prefix() {
        return prefix;
    }

    private static String normalize(String raw) {
        if (!StringUtils.hasText(raw)) {
            return "hei";
        }
        String value = raw.trim();
        while (value.endsWith(":")) {
            value = value.substring(0, value.length() - 1);
        }
        return value.isEmpty() ? "hei" : value;
    }
}
