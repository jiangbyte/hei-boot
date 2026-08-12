package github.jiangbyte.io.sys.modules.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.core.util.BatchPartition;
import github.jiangbyte.io.common.mybatis.datasource.ReadDataSource;
import github.jiangbyte.io.common.oss.StorageService;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.sys.config.RuntimeSettings;
import github.jiangbyte.io.sys.config.RuntimeSettingsHolder;
import github.jiangbyte.io.sys.modules.file.convert.SysFileConvert;
import github.jiangbyte.io.sys.modules.file.entity.SysFile;
import github.jiangbyte.io.sys.modules.file.mapper.SysFileMapper;
import github.jiangbyte.io.sys.modules.file.param.SysFileEditParam;
import github.jiangbyte.io.sys.modules.file.param.SysFilePageParam;
import github.jiangbyte.io.sys.modules.file.result.SysFileUrlResult;
import github.jiangbyte.io.sys.modules.file.service.FileService;
import github.jiangbyte.io.sys.modules.file.support.FileAccessUrls;
import github.jiangbyte.io.sys.modules.storage.ResolvedStorageConfig;
import github.jiangbyte.io.sys.modules.storage.StorageEngineFactory;
import github.jiangbyte.io.sys.modules.storage.StorageSettingsResolver;
import github.jiangbyte.io.sys.modules.storage.StorageSettingsResolverImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 文件服务实现：对接存储引擎与元数据持久化。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements FileService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final StorageSettingsResolver storageSettingsResolver;
    private final StorageEngineFactory storageEngineFactory;
    private final SysFileConvert fileConvert;
    private final FileAccessUrls fileAccessUrls;

    @Override
    @Transactional
    public SysFile upload(MultipartFile file, String storageProvider) {
        return doUpload(file, storageProvider);
    }

    @Override
    @Transactional
    public void delete(IdsParam param) {
        List<String> ids = param.getIds();
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 分批加载 → 先删存储对象 → 再删库记录
        for (List<String> batch : BatchPartition.partition(ids)) {
            List<SysFile> files = getBaseMapper().selectByIds(batch);
            for (SysFile file : files) {
                storageFor(file).delete(file.getObjectName());
            }
            this.removeByIds(batch);
        }
    }

    @Override
    @Transactional
    public void update(SysFileEditParam param) {
        // 按主键加载
        SysFile file = this.getById(param.getId());
        if (file == null) {
            // 不存在则抛出业务异常
            throw new BizException(404, "File not found");
        }
        fileConvert.update(param, file);
        this.updateById(file);
    }

    @Override
    @ReadDataSource
    public SysFile detail(String id) {
        // 按主键加载
        SysFile file = this.getById(id);
        if (file == null) {
            // 不存在则抛出业务异常
            throw new BizException(404, "File not found");
        }
        return withResolvedUrl(file);
    }

    @Override
    public void assertOwnedByCurrent(SysFile file) {
        if (file == null) {
            throw new BizException(404, "File not found");
        }
        String accountId = LoginHelper.requireUser().getAccountId();
        if (!StringUtils.hasText(file.getCreatedBy()) || !accountId.equals(file.getCreatedBy())) {
            throw new BizException(403, "无权访问该文件");
        }
    }

    @Override
    @ReadDataSource
    public List<SysFile> listByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        List<SysFile> result = new ArrayList<>();
        for (List<String> batch : BatchPartition.partition(ids)) {
            getBaseMapper().selectByIds(batch).forEach(file -> result.add(withResolvedUrl(file)));
        }
        return result;
    }

    @Override
    public Resource download(String id) {
        SysFile file = detail(id);
        return storageFor(file).load(file.getObjectName());
    }

    @Override
    @ReadDataSource
    public SysFileUrlResult url(String objectName) {
        // 校验唯一性
        SysFile file = getBaseMapper().selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getObjectName, objectName).last("limit 1"));
        // 始终重新生成访问 URL（fastapi get_url → storage.get_object_url）。
        // 库中 url 可能是已过期的 S3 预签名，不能原样返回。
        return new SysFileUrlResult(objectName, storageFor(file).publicUrl(objectName));
    }

    @Override
    @ReadDataSource
    public SysFileUrlResult presignedUrl(String objectName) {
        // 校验唯一性
        SysFile file = getBaseMapper().selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getObjectName, objectName).last("limit 1"));
        int expireSeconds = RuntimeSettingsHolder.get().getInt(
                StorageSettingsResolverImpl.KEY_PRESIGN_EXPIRE_SECONDS,
                StorageSettingsResolverImpl.DEFAULT_PRESIGN_EXPIRE_SECONDS);
        Duration ttl = Duration.ofSeconds(Math.max(1, expireSeconds));
        return new SysFileUrlResult(objectName, storageFor(file).presignedUrl(objectName, ttl));
    }

    @Override
    @ReadDataSource
    public Page<SysFile> page(SysFilePageParam param) {
        // 分页查询
        Page<SysFile> page = this.getBaseMapper().selectPage(new Page<>(param.getCurrent(), param.getSize()),
                Wrappers.<SysFile>lambdaQuery()
                        .like(StringUtils.hasText(param.getOriginalName()), SysFile::getOriginalName, param.getOriginalName())
                        .like(StringUtils.hasText(param.getObjectName()), SysFile::getObjectName, param.getObjectName())
                        .eq(StringUtils.hasText(param.getStorageProvider()), SysFile::getStorageProvider, param.getStorageProvider())
                        .like(StringUtils.hasText(param.getContentType()), SysFile::getContentType, param.getContentType())
                        .orderByDesc(SysFile::getCreatedAt));
        page.getRecords().forEach(this::withResolvedUrl);
        return page;
    }

    @Override
    public Resource publicDownload(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            throw new BizException(400, "object_name required");
        }
        if (objectName.contains("..") || objectName.startsWith("/") || objectName.contains("\\")) {
            throw new BizException(400, "Invalid object_name");
        }
        SysFile file = getBaseMapper().selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getObjectName, objectName)
                .last("limit 1"));
        if (file == null) {
            // 不存在则抛出业务异常
            throw new BizException(404, "File not found");
        }
        return storageFor(file).load(file.getObjectName());
    }

    @Override
    @Transactional
    public void deleteByObjectName(String objectName) {
        if (!StringUtils.hasText(objectName) || objectName.startsWith("http://") || objectName.startsWith("https://")) {
            return;
        }
        // 按 objectName 加载
        SysFile file = getBaseMapper().selectOne(Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getObjectName, objectName).last("limit 1"));
        if (file == null) {
            return;
        }
        // 删存储后删库
        storageFor(file).delete(objectName);
        this.removeById(file.getId());
    }

    @Override
    @ReadDataSource
    public String resolveAccessUrl(String objectNameOrUrl) {
        // 跨模块：路径风格 /api/v1/files/...（fastapi resolve_file_url；前端 FILES_PUBLIC_PATH）
        return fileAccessUrls.resolveFileUrl(objectNameOrUrl);
    }

    @Override
    @ReadDataSource
    public List<SysFile> listByObjectNames(Collection<String> objectNames) {
        if (objectNames == null || objectNames.isEmpty()) {
            return List.of();
        }
        List<String> normalized = new ArrayList<>();
        for (String raw : objectNames) {
            String name = fileAccessUrls.normalizeObjectName(raw);
            if (StringUtils.hasText(name) && !fileAccessUrls.isExternalUrl(name)) {
                normalized.add(name);
            }
        }
        if (normalized.isEmpty()) {
            return List.of();
        }
        List<SysFile> result = new ArrayList<>();
        for (List<String> batch : BatchPartition.partition(normalized)) {
            // 组装查询条件
            getBaseMapper().selectList(Wrappers.<SysFile>lambdaQuery()
                            .in(SysFile::getObjectName, batch))
                    .forEach(file -> {
                        // 跨模块消费优先稳定公网路径（非 S3 预签名）。
                        String access = fileAccessUrls.resolveFileUrl(file.getObjectName());
                        if (StringUtils.hasText(access)) {
                            file.setUrl(access);
                        }
                        result.add(file);
                    });
        }
        return result;
    }

    private SysFile doUpload(MultipartFile file, String storageProvider) {
        if (file == null || file.isEmpty()) {
            throw new BizException("File is required");
        }
        // 解析存储配置并校验上传
        ResolvedStorageConfig storageConfig = storageSettingsResolver.resolve(storageProvider);
        validateUpload(file, storageConfig);
        String originalName = safeOriginalName(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String objectName = buildObjectName(originalName, "uploads");
        try {
            String contentType = sanitizeContentType(file.getContentType());
            StorageService storage = storageEngineFactory.get(storageConfig);
            // 写入对象存储
            String url = storage.put(objectName, file.getInputStream(), file.getSize(), contentType);
            // 落库元数据并解析访问 URL
            SysFile entity = new SysFile();
            entity.setObjectName(objectName);
            entity.setOriginalName(originalName);
            entity.setStorageProvider(storageConfig.getProvider());
            entity.setBucket(storageConfig.getBucket());
            entity.setContentType(contentType);
            entity.setSize(file.getSize());
            entity.setUrl(url);
            this.save(entity);
            return withResolvedUrl(entity);
        } catch (IOException exception) {
            throw new BizException(500, "Failed to read upload stream");
        }
    }

    private SysFile withResolvedUrl(SysFile file) {
        if (file == null || !StringUtils.hasText(file.getObjectName())) {
            return file;
        }
        String resolved = storageFor(file).publicUrl(file.getObjectName());
        if (StringUtils.hasText(resolved)) {
            file.setUrl(resolved);
        }
        return file;
    }

    private void validateUpload(MultipartFile file, ResolvedStorageConfig storageConfig) {
        RuntimeSettings settings = RuntimeSettingsHolder.get();
        // 校验大小
        long maxBytes = storageConfig.getUploadMaxBytes() > 0
                ? storageConfig.getUploadMaxBytes()
                : StorageSettingsResolverImpl.DEFAULT_UPLOAD_MAX_BYTES;
        if (file.getSize() > maxBytes) {
            throw new BizException(400, "File exceeds max size");
        }
        // 校验扩展名黑白名单
        String originalName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
        String ext = extensionOf(originalName);
        List<String> denied = readJsonStringList(settings.get("STORAGE_UPLOAD_DENIED_EXTENSIONS", "[]"));
        if (!denied.isEmpty() && denied.stream().anyMatch(e -> normalizeExt(e).equals(ext))) {
            throw new BizException(400, "File extension not allowed");
        }
        List<String> allowedExt = readJsonStringList(settings.get("STORAGE_UPLOAD_ALLOWED_EXTENSIONS", "[]"));
        if (!allowedExt.isEmpty() && allowedExt.stream().noneMatch(e -> normalizeExt(e).equals(ext))) {
            throw new BizException(400, "File extension not allowed");
        }
        // 校验 Content-Type
        String contentType = file.getContentType();
        List<String> allowedTypes = readJsonStringList(settings.get("STORAGE_UPLOAD_ALLOWED_CONTENT_TYPES", "[]"));
        if (StringUtils.hasText(contentType)
                && !allowedTypes.isEmpty()
                && allowedTypes.stream().noneMatch(t -> t.equalsIgnoreCase(contentType))) {
            throw new BizException(400, "Content type not allowed");
        }
    }

    private StorageService storageFor(SysFile file) {
        if (file != null && StringUtils.hasText(file.getStorageProvider())) {
            return storageEngineFactory.get(file.getStorageProvider());
        }
        return storageEngineFactory.getDefault();
    }

    private static String normalizeExt(String ext) {
        if (ext == null) {
            return "";
        }
        String value = ext.trim().toLowerCase(Locale.ROOT);
        return value.startsWith(".") ? value : "." + value;
    }

    private static List<String> readJsonStringList(String json) {
        if (!StringUtils.hasText(json)) {
            return List.of();
        }
        try {
            return MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (Exception ex) {
            return List.of();
        }
    }

    private static String extensionOf(String filename) {
        String safe = filename.replace("\\", "/");
        int slash = safe.lastIndexOf('/');
        if (slash >= 0) {
            safe = safe.substring(slash + 1);
        }
        int dot = safe.lastIndexOf('.');
        return dot >= 0 ? safe.substring(dot).toLowerCase(Locale.ROOT) : "";
    }

    private static String safeOriginalName(String filename) {
        String safe = filename.replace("\\", "/");
        int slash = safe.lastIndexOf('/');
        if (slash >= 0) {
            safe = safe.substring(slash + 1);
        }
        return safe.isBlank() ? "file" : safe;
    }

    private static String sanitizeContentType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return "application/octet-stream";
        }
        return contentType.trim();
    }

    private String buildObjectName(String filename, String category) {
        RuntimeSettings settings = RuntimeSettingsHolder.get();
        int maxCategoryLen = Math.max(1, settings.getInt("STORAGE_UPLOAD_CATEGORY_MAX_LENGTH", 64));
        String safeCategory = StringUtils.hasText(category) ? category.trim() : "uploads";
        if (safeCategory.length() > maxCategoryLen) {
            throw new BizException(400, "Upload category exceeds max length");
        }
        String safeName = filename.replace("\\", "/");
        int slash = safeName.lastIndexOf('/');
        if (slash >= 0) {
            safeName = safeName.substring(slash + 1);
        }
        int dot = safeName.lastIndexOf('.');
        String suffix = dot >= 0 ? safeName.substring(dot).toLowerCase(Locale.ROOT) : "";
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return "%s/%04d/%02d/%02d/%s%s".formatted(
                safeCategory,
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth(),
                UUID.randomUUID().toString().replace("-", ""),
                suffix);
    }
}
