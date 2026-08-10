package github.jiangbyte.io.common.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象存储配置属性：类型、本地路径、S3 端点与凭证等。
 *
 * Author: Charlie
 */
@Data
@ConfigurationProperties(prefix = "hei.storage")
public class OssProperties {

    private String type = "local";
    private Local local = new Local();
    private S3 s3 = new S3();
    private Upload upload = new Upload();

    @Data
    public static class Local {
        private String basePath = "./storage";
        private String publicBaseUrl = "/api/v1/files";
        /**
         * 可选绝对公网基址（CDN / 站点源）；为空则仅用相对 publicBaseUrl。
         */
        private String baseUrl = "";
    }

    @Data
    public static class S3 {
        private String bucket;
        private String region = "us-east-1";
        private String endpoint;
        /** 绝对公网源/CDN（可选）。为空 → 预签名完整 URL（对齐 fastapi）。 */
        private String publicBaseUrl;
        /**
         * base 为空时 S3 publicUrl 不使用（保留给本地/代理工具）。
         */
        private String publicPath = "/api/v1/files";
        private String accessKey;
        private String secretKey;
        /** MinIO / 自定义 endpoint 的可选 path-style 访问。 */
        private boolean pathStyleAccess = false;
        /**
         * {@link #publicBaseUrl} 为空时的预签名 TTL 秒数（默认 3600）。
         */
        private int presignExpireSeconds = 3600;
    }

    @Data
    public static class Upload {
        /** 最大上传字节数（默认 20 MiB）。 */
        private long maxSizeBytes = 20L * 1024 * 1024;
        private List<String> allowedExtensions = defaultExtensions();
        private List<String> allowedContentTypes = defaultContentTypes();

        private static List<String> defaultExtensions() {
            List<String> list = new ArrayList<>();
            list.add(".png");
            list.add(".jpg");
            list.add(".jpeg");
            list.add(".gif");
            list.add(".webp");
            list.add(".svg");
            list.add(".pdf");
            list.add(".txt");
            list.add(".md");
            list.add(".doc");
            list.add(".docx");
            list.add(".xls");
            list.add(".xlsx");
            list.add(".ppt");
            list.add(".pptx");
            list.add(".zip");
            list.add(".mp3");
            list.add(".mp4");
            list.add(".wav");
            return list;
        }

        private static List<String> defaultContentTypes() {
            List<String> list = new ArrayList<>();
            list.add("image/png");
            list.add("image/jpeg");
            list.add("image/gif");
            list.add("image/webp");
            list.add("image/svg+xml");
            list.add("application/pdf");
            list.add("text/plain");
            list.add("text/markdown");
            list.add("application/msword");
            list.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            list.add("application/vnd.ms-excel");
            list.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            list.add("application/vnd.ms-powerpoint");
            list.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            list.add("application/zip");
            list.add("audio/mpeg");
            list.add("audio/wav");
            list.add("video/mp4");
            list.add("application/octet-stream");
            return list;
        }
    }
}
