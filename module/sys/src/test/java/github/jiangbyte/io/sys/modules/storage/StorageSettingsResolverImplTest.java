package github.jiangbyte.io.sys.modules.storage;

/**
 * Author: Charlie
 **/

import github.jiangbyte.io.sys.config.RuntimeSettings;
import github.jiangbyte.io.sys.config.RuntimeSettingsHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageSettingsResolverImplTest {

    private StorageSettingsResolverImpl resolver;

    @BeforeEach
    void setUp() {
        resolver = new StorageSettingsResolverImpl();
        RuntimeSettingsHolder.bindLoader(() -> Map.of());
    }

    @AfterEach
    void tearDown() {
        RuntimeSettingsHolder.bindLoader(() -> Map.of());
    }

    @Test
    void resolvesLocalFromDefaultFileEngine() {
        bind(Map.of(
                "DEFAULT_FILE_ENGINE", "LOCAL",
                "STORAGE_UPLOAD_MAX_BYTES", "10485760",
                "STORAGE_LOCAL_LOCAL_ROOT", "./storage",
                "STORAGE_LOCAL_PUBLIC_PATH", "/api/v1/files",
                "STORAGE_LOCAL_BASE_URL", ""
        ));

        ResolvedStorageConfig config = resolver.resolveDefault();

        assertEquals("local", config.getId());
        assertEquals("LOCAL", config.getEngine());
        assertEquals("local", config.getProvider());
        assertEquals("./storage", config.getLocalRoot());
        assertEquals("/api/v1/files", config.getPublicPath());
        assertEquals(10_485_760L, config.getUploadMaxBytes());
    }

    @Test
    void usesSeedDefaultsWhenKeysMissing() {
        bind(Map.of());

        ResolvedStorageConfig config = resolver.resolveDefault();

        assertEquals("local", config.getProvider());
        assertEquals("./storage", config.getLocalRoot());
        assertEquals("/api/v1/files", config.getPublicPath());
        assertEquals(10_485_760L, config.getUploadMaxBytes());
    }

    @Test
    void mapsMinioEngineToS3CompatibleProvider() {
        bind(Map.of(
                "DEFAULT_FILE_ENGINE", "MINIO",
                "STORAGE_MINIO_BUCKET", "bucket",
                "STORAGE_MINIO_ENDPOINT", "http://127.0.0.1:9000",
                "STORAGE_MINIO_ACCESS_KEY", "ak",
                "STORAGE_MINIO_SECRET_KEY", "sk",
                "STORAGE_MINIO_USE_SSL", "false"
        ));

        ResolvedStorageConfig config = resolver.resolveDefault();

        assertEquals("minio", config.getProvider());
        assertEquals("MINIO", config.getEngine());
        assertEquals("bucket", config.getBucket());
        assertTrue(config.isPathStyleAccess());
        assertEquals("s3", FileEngines.toOssType(config.getProvider()));
    }

    private static void bind(Map<String, String> values) {
        Map<String, String> copy = new HashMap<>(values);
        RuntimeSettingsHolder.bindLoader(() -> copy);
        // 强制版本号递增
        RuntimeSettingsHolder.reload();
        RuntimeSettings ignored = RuntimeSettingsHolder.get();
        assertEquals(copy, ignored.asMap());
    }
}
