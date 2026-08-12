package github.jiangbyte.io.common.core.sensitive;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 敏感键名匹配工具。
 *
 * Author: Charlie
 */
public final class SensitiveKeys {

    public static final Set<String> DEFAULT = Set.of(
            "password",
            "secret",
            "token",
            "cryptokey",
            "crypto-key",
            "accesskey",
            "access-key",
            "privatekey",
            "private-key",
            "authorization");

    private SensitiveKeys() {
    }

    public static Set<String> normalize(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return DEFAULT;
        }
        return keys.stream()
                .filter(k -> k != null && !k.isBlank())
                .map(SensitiveKeys::canonical)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static Set<String> normalizeCsv(String csv) {
        if (csv == null || csv.isBlank()) {
            return DEFAULT;
        }
        return normalize(Arrays.asList(csv.split(",")));
    }

    public static boolean matches(String name, Set<String> keys) {
        if (name == null || name.isBlank()) {
            return false;
        }
        String c = canonical(name);
        if (keys.contains(c)) {
            return true;
        }
        for (String key : keys) {
            if (c.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private static String canonical(String name) {
        return name.trim().toLowerCase(Locale.ROOT).replace("_", "").replace("-", "");
    }
}
