package github.jiangbyte.io.sys.modules.file.support;

import github.jiangbyte.io.sys.modules.storage.ResolvedStorageConfig;
import github.jiangbyte.io.sys.modules.storage.StorageSettingsResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 文件访问 URL 组装工具。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class FileAccessUrls {

    private final StorageSettingsResolver storageSettingsResolver;

    public boolean isExternalUrl(String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        try {
            String scheme = URI.create(value.trim()).getScheme();
            if (scheme == null) {
                return false;
            }
            String lower = scheme.toLowerCase(Locale.ROOT);
            return "http".equals(lower) || "https".equals(lower) || "data".equals(lower) || "blob".equals(lower);
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    /** 规范化对象名。 */
    public String normalizeObjectName(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String raw = value.trim();
        if (isExternalUrl(raw)) {
            return raw;
        }
        String publicPath = defaultPublicPath().replaceAll("/+$", "");
        String pathOnly;
        try {
            pathOnly = raw.contains("://") ? URI.create(raw).getPath() : raw;
        } catch (IllegalArgumentException ex) {
            pathOnly = raw;
        }
        if (pathOnly == null) {
            return null;
        }
        pathOnly = pathOnly.replace('\\', '/');
        String prefix = publicPath + "/";
        if (pathOnly.startsWith(prefix)) {
            String stripped = pathOnly.substring(prefix.length()).replaceAll("^/+", "");
            return StringUtils.hasText(stripped) ? stripped : null;
        }
        if (pathOnly.equals(publicPath)) {
            return null;
        }
        String normalized = pathOnly.replaceAll("^/+", "");
        return StringUtils.hasText(normalized) ? normalized : null;
    }

    public String buildAccessUrl(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            return null;
        }
        if (isExternalUrl(objectName)) {
            return objectName;
        }
        String publicPath = defaultPublicPath().replaceAll("/+$", "");
        if (!publicPath.startsWith("/")) {
            publicPath = "/" + publicPath;
        }
        String path = publicPath + "/" + quoteObjectName(objectName);
        ResolvedStorageConfig config = storageSettingsResolver.resolveDefault();
        String baseUrl = config == null ? null : config.getBaseUrl();
        if (!StringUtils.hasText(baseUrl)) {
            return path;
        }
        String origin = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl.trim();
        return origin + path;
    }

    /**
     * 把任意形式的对象引用转成纯 object key（用于存储引擎删除/加载）：
     * 支持 纯 key（uploads/2024/01/x.jpg）、/api/v1/files/... 路径、完整 http(s) URL。
     */
    public String toObjectKey(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        String raw = value.trim();
        String pathOnly;
        try {
            pathOnly = raw.contains("://") ? URI.create(raw).getPath() : raw;
        } catch (IllegalArgumentException ex) {
            pathOnly = raw;
        }
        if (pathOnly == null) {
            return null;
        }
        pathOnly = pathOnly.replace('\\', '/');
        // 去掉公开路径前缀（/api/v1/files/...）
        String publicPath = defaultPublicPath().replaceAll("/+$", "");
        String prefix = publicPath + "/";
        if (pathOnly.startsWith(prefix)) {
            pathOnly = pathOnly.substring(prefix.length());
        } else if (pathOnly.equals(publicPath)) {
            return null;
        }
        String key = pathOnly.replaceAll("^/+", "");
        return StringUtils.hasText(key) ? key : null;
    }

    public String resolveFileUrl(String value) {
        String objectName = normalizeObjectName(value);
        if (!StringUtils.hasText(objectName)) {
            return null;
        }
        if (isExternalUrl(objectName)) {
            return objectName;
        }
        return buildAccessUrl(objectName);
    }

    public String defaultPublicPath() {
        // 解析存储配置
        ResolvedStorageConfig config = storageSettingsResolver.resolveDefault();
        String publicPath = config == null ? null : config.getPublicPath();
        return StringUtils.hasText(publicPath) ? publicPath.trim() : "/api/v1/files";
    }

    static String quoteObjectName(String objectName) {
        return Arrays.stream(objectName.replace('\\', '/').split("/"))
                .filter(StringUtils::hasText)
                .map(part -> URLEncoder.encode(part, StandardCharsets.UTF_8).replace("+", "%20"))
                .collect(Collectors.joining("/"));
    }
}