package github.jiangbyte.io.sys.modules.storage;

import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.sys.config.RuntimeSettings;
import github.jiangbyte.io.sys.config.RuntimeSettingsHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 存储配置解析实现：从 RuntimeSettings 组装 ResolvedStorageConfig。
 *
 * Author: Charlie
 */
@Service
public class StorageSettingsResolverImpl implements StorageSettingsResolver {

    public static final String KEY_DEFAULT_FILE_ENGINE = "DEFAULT_FILE_ENGINE";
    public static final String KEY_UPLOAD_MAX_BYTES = "STORAGE_UPLOAD_MAX_BYTES";
    public static final String KEY_PRESIGN_EXPIRE_SECONDS = "STORAGE_PRESIGN_EXPIRE_SECONDS";

    public static final String DEFAULT_ENGINE = "LOCAL";
    public static final long DEFAULT_UPLOAD_MAX_BYTES = 10_485_760L;
    public static final int DEFAULT_PRESIGN_EXPIRE_SECONDS = 3600;

    @Override
    public ResolvedStorageConfig resolveDefault() {
        return resolve(null);
    }

    @Override
    public ResolvedStorageConfig resolve(String storageProvider) {
        // 读取运行时配置并解析提供方
        RuntimeSettings settings = RuntimeSettingsHolder.get();
        String provider = FileEngines.resolveProvider(storageProvider);
        if (provider == null) {
            String engine = settings.get(KEY_DEFAULT_FILE_ENGINE, DEFAULT_ENGINE);
            provider = FileEngines.engineToProvider(engine);
        }
        if (provider == null) {
            provider = FileEngines.PROVIDER_LOCAL;
        }
        return buildForProvider(provider, settings);
    }

    private ResolvedStorageConfig buildForProvider(String provider, RuntimeSettings settings) {
        String engine = FileEngines.providerToEngine(provider);
        if (engine == null) {
            throw new BizException(400, "Unknown storage provider: " + provider);
        }
        long uploadMax = settings.getLong(KEY_UPLOAD_MAX_BYTES, DEFAULT_UPLOAD_MAX_BYTES);

        if (FileEngines.isLocal(provider)) {
            // 组装本地存储快照
            String localRoot = cfg(settings, provider, "LOCAL_ROOT");
            if (!StringUtils.hasText(localRoot)) {
                localRoot = "./storage";
            }
            return ResolvedStorageConfig.builder()
                    .id(provider)
                    .engine(engine)
                    .provider(provider)
                    .localRoot(localRoot)
                    .windowsRoot(cfg(settings, provider, "WINDOWS_ROOT"))
                    .publicPath(cfgOr(settings, provider, "PUBLIC_PATH", "/api/v1/files"))
                    .baseUrl(cfg(settings, provider, "BASE_URL"))
                    .uploadMaxBytes(uploadMax)
                    .presignExpireSeconds(settings.getInt(KEY_PRESIGN_EXPIRE_SECONDS, DEFAULT_PRESIGN_EXPIRE_SECONDS))
                    .build();
        }

        // 组装对象存储快照
        boolean pathStyle = FileEngines.PROVIDER_MINIO.equals(provider)
                || FileEngines.PROVIDER_RUSTFS.equals(provider);
        String region = cfg(settings, provider, "REGION");
        if (!StringUtils.hasText(region)) {
            region = FileEngines.PROVIDER_RUSTFS.equals(provider) ? "us-east-1" : "us-east-1";
        }
        return ResolvedStorageConfig.builder()
                .id(provider)
                .engine(engine)
                .provider(provider)
                .bucket(cfg(settings, provider, "BUCKET"))
                .endpoint(cfg(settings, provider, "ENDPOINT"))
                .accessKey(cfg(settings, provider, "ACCESS_KEY"))
                .secretKey(cfg(settings, provider, "SECRET_KEY"))
                .region(region)
                .useSsl(parseBool(cfg(settings, provider, "USE_SSL"), true))
                .pathStyleAccess(pathStyle)
                .baseUrl(cfg(settings, provider, "BASE_URL"))
                .publicPath(cfgOr(settings, provider, "PUBLIC_PATH", "/api/v1/files"))
                .uploadMaxBytes(uploadMax)
                .presignExpireSeconds(settings.getInt(KEY_PRESIGN_EXPIRE_SECONDS, DEFAULT_PRESIGN_EXPIRE_SECONDS))
                .build();
    }

    private static String cfg(RuntimeSettings settings, String provider, String suffix) {
        return settings.get(FileEngines.configKey(provider, suffix));
    }

    private static String cfgOr(RuntimeSettings settings, String provider, String suffix, String fallback) {
        String value = cfg(settings, provider, suffix);
        return StringUtils.hasText(value) ? value : fallback;
    }

    private static boolean parseBool(String raw, boolean defaultValue) {
        if (!StringUtils.hasText(raw)) {
            return defaultValue;
        }
        String normalized = raw.trim().toLowerCase();
        if ("true".equals(normalized) || "1".equals(normalized) || "yes".equals(normalized)) {
            return true;
        }
        if ("false".equals(normalized) || "0".equals(normalized) || "no".equals(normalized)) {
            return false;
        }
        return defaultValue;
    }
}
