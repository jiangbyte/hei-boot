package github.jiangbyte.io.common.oss.local;

/** Author: Charlie **/

import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.oss.config.OssProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalStorageServiceTest {

    @TempDir
    Path tempDir;

    private LocalStorageService storageService;

    @BeforeEach
    void setUp() {
        OssProperties properties = new OssProperties();
        properties.getLocal().setBasePath(tempDir.toString());
        storageService = new LocalStorageService(properties);
    }

    @Test
    void rejectsPathTraversalObjectKey() {
        BizException ex = assertThrows(BizException.class, () -> storageService.load("../etc/passwd"));
        assertEquals(400, ex.getCode());
    }

    @Test
    void rejectsColonInObjectKey() {
        BizException ex = assertThrows(BizException.class, () -> storageService.load("C:/windows/system32"));
        assertEquals(400, ex.getCode());
    }

    @Test
    void publicUrlUsesBaseUrlWhenSet() {
        OssProperties properties = new OssProperties();
        properties.getLocal().setBasePath(tempDir.toString());
        properties.getLocal().setPublicBaseUrl("/api/v1/files");
        properties.getLocal().setBaseUrl("https://cdn.example.com");
        LocalStorageService service = new LocalStorageService(properties);
        assertEquals("https://cdn.example.com/api/v1/files/a/b.png", service.publicUrl("a/b.png"));
    }

    @Test
    void publicUrlUsesPathStyleRelativeByDefault() {
        assertEquals("/api/v1/files/a/b.png", storageService.publicUrl("a/b.png"));
    }
}
