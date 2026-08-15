package github.jiangbyte.io.sys.modules.file.support;

/**
 * Author: Charlie
 **/

import github.jiangbyte.io.sys.modules.storage.ResolvedStorageConfig;
import github.jiangbyte.io.sys.modules.storage.StorageSettingsResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileAccessUrlsTest {

    private FileAccessUrls urls;

    @BeforeEach
    void setUp() {
        StorageSettingsResolver resolver = mock(StorageSettingsResolver.class);
        when(resolver.resolveDefault()).thenReturn(ResolvedStorageConfig.builder()
                .id("local")
                .engine("LOCAL")
                .provider("local")
                .publicPath("/api/v1/files")
                .uploadMaxBytes(1024)
                .build());
        urls = new FileAccessUrls(resolver);
    }

    @Test
    void keepsExternalUrl() {
        assertTrue(urls.isExternalUrl("https://cdn.example.com/a.png"));
        assertEquals("https://cdn.example.com/a.png", urls.normalizeObjectName("https://cdn.example.com/a.png"));
    }

    @Test
    void stripsPublicPathPrefix() {
        assertEquals("uploads/a.png", urls.normalizeObjectName("/api/v1/files/uploads/a.png"));
        assertEquals("uploads/a.png", urls.normalizeObjectName("uploads/a.png"));
    }

    @Test
    void blankBecomesNull() {
        assertNull(urls.normalizeObjectName(" "));
        assertNull(urls.normalizeObjectName(null));
    }

    @Test
    void toObjectKeyNormalizesAllForms() {
        // 纯 key 原样
        assertEquals("uploads/a.png", urls.toObjectKey("uploads/a.png"));
        // 公开路径前缀剥离
        assertEquals("uploads/a.png", urls.toObjectKey("/api/v1/files/uploads/a.png"));
        // 完整 URL：提取 path 并剥离公开前缀
        assertEquals("uploads/a.png", urls.toObjectKey("http://localhost:8000/api/v1/files/uploads/a.png"));
        // 空值
        assertNull(urls.toObjectKey(" "));
        assertNull(urls.toObjectKey(null));
    }

    @Test
    void resolveFileUrlMatchesFrontendPublicPath() {
        assertEquals("/api/v1/files/uploads/a.png", urls.resolveFileUrl("uploads/a.png"));
        assertEquals(
                "/api/v1/files/uploads/a%20b.png",
                urls.resolveFileUrl("uploads/a b.png"));
        assertEquals("https://cdn.example.com/x.png", urls.resolveFileUrl("https://cdn.example.com/x.png"));
    }
}