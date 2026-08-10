package github.jiangbyte.io.sys.modules.file.service;

/** Author: Charlie **/

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.oss.StorageService;
import github.jiangbyte.io.sys.modules.file.convert.SysFileConvert;
import github.jiangbyte.io.sys.modules.file.entity.SysFile;
import github.jiangbyte.io.sys.modules.file.mapper.SysFileMapper;
import github.jiangbyte.io.sys.modules.file.service.impl.FileServiceImpl;
import github.jiangbyte.io.sys.modules.storage.StorageEngineFactory;
import github.jiangbyte.io.sys.modules.storage.StorageSettingsResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServicePublicDownloadTest {

    @Mock
    private SysFileMapper fileMapper;
    @Mock
    private StorageSettingsResolver storageSettingsResolver;
    @Mock
    private StorageEngineFactory storageEngineFactory;
    @Mock
    private StorageService storageService;
    @Mock
    private SysFileConvert fileConvert;
    @Mock
    private github.jiangbyte.io.sys.modules.file.support.FileAccessUrls fileAccessUrls;

    private FileService fileService;

    @BeforeEach
    void setUp() {
        FileServiceImpl impl = new FileServiceImpl(
                storageSettingsResolver,
                storageEngineFactory,
                fileConvert,
                fileAccessUrls);
        ReflectionTestUtils.setField(impl, "baseMapper", fileMapper);
        fileService = impl;
    }

    @Test
    void rejectsTraversalObjectName() {
        BizException ex = assertThrows(BizException.class, () -> fileService.publicDownload("../secret"));
        assertEquals(400, ex.getCode());
    }

    @Test
    void requiresDatabaseRow() {
        when(fileMapper.selectOne(any(Wrapper.class))).thenReturn(null);
        BizException ex = assertThrows(BizException.class, () -> fileService.publicDownload("a/b.png"));
        assertEquals(404, ex.getCode());
    }

    @Test
    void loadsViaStorageEngineFactory() {
        SysFile file = new SysFile();
        file.setObjectName("a/b.png");
        file.setStorageProvider("minio");
        when(fileMapper.selectOne(any(Wrapper.class))).thenReturn(file);
        when(storageEngineFactory.get("minio")).thenReturn(storageService);
        when(storageService.load("a/b.png")).thenReturn(new ByteArrayResource("ok".getBytes()));

        assertEquals("ok", new String(((ByteArrayResource) fileService.publicDownload("a/b.png")).getByteArray()));
        verify(storageService).load("a/b.png");
    }
}
