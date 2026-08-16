package github.jiangbyte.io.sys.modules.file.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import github.jiangbyte.io.common.job.JobHandler;
import github.jiangbyte.io.common.oss.StorageService;
import github.jiangbyte.io.common.oss.StorageService.LocalObjectEntry;
import github.jiangbyte.io.common.oss.local.LocalStorageService;
import github.jiangbyte.io.sys.modules.file.entity.SysFile;
import github.jiangbyte.io.sys.modules.file.mapper.SysFileMapper;
import github.jiangbyte.io.sys.modules.storage.FileEngines;
import github.jiangbyte.io.sys.modules.storage.StorageEngineFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 本地存储孤立对象清理任务：删除早于保留期且无 sys_file 元数据行的本地文件
 * （对齐 hei-fastapi sysFileCleanupLocalOrphans）。
 * 执行参数（JSON）：{"minAgeMinutes": 60}，兼容旧纯数字传参（保留分钟数）。
 *
 * Author: Charlie
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysFileCleanupLocalOrphansJob implements JobHandler {

    private static final long DEFAULT_MIN_AGE_SECONDS = 3600L;
    private static final long DEFAULT_LIMIT = 200L;
    /** 最小保护窗：避免误删刚上传、元数据尚未落库的文件。 */
    private static final long MIN_PROTECT_WINDOW_SECONDS = 300L;

    private final StorageEngineFactory storageEngineFactory;
    private final SysFileMapper fileMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String execute(String params) {
        long minAgeSeconds = DEFAULT_MIN_AGE_SECONDS;
        Long minAgeMinutes = readLongParam(params, "minAgeMinutes");
        if (minAgeMinutes != null) {
            minAgeSeconds = minAgeMinutes * 60L;
        }
        if (minAgeSeconds < MIN_PROTECT_WINDOW_SECONDS) {
            minAgeSeconds = MIN_PROTECT_WINDOW_SECONDS;
        }
        long limit = DEFAULT_LIMIT;
        long cutoffMillis = System.currentTimeMillis() - minAgeSeconds * 1000L;

        // 解析本地存储引擎；非本地引擎直接跳过
        StorageService service = storageEngineFactory.get(FileEngines.PROVIDER_LOCAL);
        if (!(service instanceof LocalStorageService local)) {
            log.info("sysFileCleanupLocalOrphans skipped: storage engine is not local");
            return "skipped: not local";
        }

        List<LocalObjectEntry> all = local.listLocalObjects();
        List<LocalObjectEntry> candidates = new ArrayList<>();
        for (LocalObjectEntry candidate : all) {
            if (candidate.lastModifiedMillis() >= cutoffMillis) {
                continue;
            }
            candidates.add(candidate);
            if (candidates.size() >= limit * 5) {
                break;
            }
        }
        if (candidates.isEmpty()) {
            return "scanned=0,deleted=0,skipped=0";
        }

        List<String> keys = candidates.stream().map(LocalObjectEntry::objectKey).toList();
        List<SysFile> existing = fileMapper.selectList(Wrappers.<SysFile>lambdaQuery()
                .select(SysFile::getObjectName)
                .in(SysFile::getObjectName, keys));
        Set<String> existSet = new HashSet<>();
        for (SysFile file : existing) {
            existSet.add(file.getObjectName());
        }

        int deleted = 0;
        int skipped = 0;
        for (LocalObjectEntry candidate : candidates) {
            if (existSet.contains(candidate.objectKey())) {
                skipped++;
                continue;
            }
            try {
                local.delete(candidate.objectKey());
                deleted++;
            } catch (Exception ex) {
                log.warn("Local orphan delete failed: {}", candidate.objectKey());
            }
        }
        log.info("sysFileCleanupLocalOrphans scanned={}, deleted={}, skipped={}",
                candidates.size(), deleted, skipped);
        return "scanned=" + candidates.size() + ",deleted=" + deleted
                + ",skipped=" + skipped;
    }

    /** 从任务参数 JSON 读取长整型字段；兼容旧纯数字传参（视为该字段值）。 */
    private Long readLongParam(String params, String key) {
        if (params == null || params.isBlank()) {
            return null;
        }
        try {
            Object value = objectMapper.readValue(params, Object.class);
            if (value instanceof Map<?, ?> map) {
                Object field = map.get(key);
                if (field == null) {
                    return null;
                }
                return Long.parseLong(String.valueOf(field).trim());
            }
            // 兼容旧纯数字传参
            return Long.parseLong(params.trim());
        } catch (Exception ignored) {
            return null;
        }
    }
}
