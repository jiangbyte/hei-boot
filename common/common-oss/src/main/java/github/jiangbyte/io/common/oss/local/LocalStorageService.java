package github.jiangbyte.io.common.oss.local;

import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.oss.StorageService;
import github.jiangbyte.io.common.oss.config.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 本地磁盘对象存储实现，适用于开发与单机部署。
 *
 * Author: Charlie
 */
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    private final OssProperties properties;

    /** 写入本地文件并返回对象键。 */
    @Override
    public String put(String objectKey, InputStream inputStream, long contentLength, String contentType) {
        Path target = resolvePath(objectKey);
        try {
            Files.createDirectories(target.getParent());
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new BizException(500, "Failed to store object locally");
        }
        return publicUrl(objectKey);
    }

    /** 删除本地对象文件。 */
    @Override
    public void delete(String objectKey) {
        try {
            Files.deleteIfExists(resolvePath(objectKey));
        } catch (IOException exception) {
            throw new BizException(500, "Failed to delete local object");
        }
    }

    /** 加载本地对象为 Resource。 */
    @Override
    public Resource load(String objectKey) {
        Path path = resolvePath(objectKey);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new BizException(404, "File not found");
        }
        return new FileSystemResource(path);
    }

    /** 返回本地对象的公开 URL。 */
    @Override
    public String publicUrl(String objectKey) {
        String publicPath = properties.getLocal().getPublicBaseUrl();
        if (publicPath == null || publicPath.isBlank()) {
            publicPath = "/api/v1/files";
        }
        String normalized = normalizeKey(objectKey);
        // 对齐 hei-fastapi：{public_path}/{object_name}（路径风格，非 query）
        String relative = publicPath.endsWith("/") ? publicPath + normalized : publicPath + "/" + normalized;
        if (!relative.startsWith("/")) {
            relative = "/" + relative;
        }
        String baseUrl = properties.getLocal().getBaseUrl();
        if (baseUrl == null || baseUrl.isBlank()) {
            return relative;
        }
        String origin = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        return origin + relative;
    }

    private Path resolvePath(String objectKey) {
        Path base = Path.of(properties.getLocal().getBasePath()).toAbsolutePath().normalize();
        Path target = base.resolve(normalizeKey(objectKey)).normalize();
        if (!target.startsWith(base)) {
            throw new BizException(400, "Invalid object path");
        }
        return target;
    }

    private String normalizeKey(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            throw new BizException(400, "object_name required");
        }
        String key = objectKey.replace("\\", "/").replaceAll("^/+", "");
        if (key.contains("..") || key.startsWith("/") || key.contains(":")) {
            throw new BizException(400, "Invalid object_name");
        }
        return key;
    }
}
