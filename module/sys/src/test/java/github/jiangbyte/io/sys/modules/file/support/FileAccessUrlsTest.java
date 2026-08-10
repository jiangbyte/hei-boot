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
    void resolveFileUrlMatchesFrontendPublicPath() {
        assertEquals("/api/v1/files/uploads/a.png", urls.resolveFileUrl("uploads/a.png"));
        assertEquals(
                "/api/v1/files/uploads/a%20b.png",
                urls.resolveFileUrl("uploads/a b.png"));
        assertEquals("https://cdn.example.com/x.png", urls.resolveFileUrl("https://cdn.example.com/x.png"));
    }
}
