package github.jiangbyte.io.common.security.web;

import org.springframework.util.StringUtils;

import java.nio.file.Path;

/**
 * 会话 Cookie Path 约定与改写辅助方法。
 *
 * Author: Charlie
 */
public final class SessionCookiePaths {

    public static final String LEGACY_ROOT_PATH = "/";
    public static final String LEGACY_COOKIE_NAME = "hei_session";

    private SessionCookiePaths() {
    }

    public static String fromRequestPath(String requestPath) {
        if (!StringUtils.hasText(requestPath)) {
            return LEGACY_ROOT_PATH;
        }
        String path = requestPath;
        while (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (!StringUtils.hasText(path) || "/".equals(path)) {
            return LEGACY_ROOT_PATH;
        }
        Path parent = Path.of(path).getParent();
        if (parent == null) {
            return path;
        }
        String parentPath = parent.toString().replace('\\', '/');
        if (!StringUtils.hasText(parentPath) || ".".equals(parentPath) || "/".equals(parentPath)) {
            return path;
        }
        if (!parentPath.startsWith("/")) {
            parentPath = "/" + parentPath;
        }
        return parentPath;
    }
}
