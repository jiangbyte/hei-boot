package github.jiangbyte.io.common.oss;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

/**
 * 对象存储抽象：上传、下载、删除与预签名 URL 等统一能力。
 *
 * Author: Charlie
 */
public interface StorageService {

    /** 上传对象并返回存储键或访问标识。 */
    String put(String objectKey, InputStream inputStream, long contentLength, String contentType);

    /** 删除指定对象。 */
    void delete(String objectKey);

    /** 加载对象为 Resource。 */
    Resource load(String objectKey);

    /** 返回对象的公开访问 URL。 */
    String publicUrl(String objectKey);

    /** 生成带过期时间的预签名访问 URL。 */
    default String presignedUrl(String objectKey, Duration ttl) {
        return publicUrl(objectKey);
    }

    /** 本地对象条目：objectKey 为相对根目录的斜杠路径。 */
    record LocalObjectEntry(String objectKey, long lastModifiedMillis) {
    }

    /** 枚举本地存储的全部对象（非本地实现返回空列表，供孤立文件清理任务使用）。 */
    default List<LocalObjectEntry> listLocalObjects() {
        return List.of();
    }
}
