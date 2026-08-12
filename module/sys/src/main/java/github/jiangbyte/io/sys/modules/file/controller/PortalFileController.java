package github.jiangbyte.io.sys.modules.file.controller;

import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.sys.modules.file.entity.SysFile;
import github.jiangbyte.io.sys.modules.file.param.SysFileObjectNameParam;
import github.jiangbyte.io.sys.modules.file.result.SysFileUrlResult;
import github.jiangbyte.io.sys.modules.file.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 门户端文件 API：上传与访问 URL。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortalFileController {

    private final FileService fileService;

    /** 上传文件。 */
    @PostMapping("/v1/portal/sys/file/upload")
    @OperationAudit(resourceType = "sys_file", action = "upload")
    public ApiResponse<SysFile> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "storage_provider", required = false) String storageProvider) {
        return ApiResponse.ok(fileService.upload(file, storageProvider));
    }

    /** 查询详情。 */
    @GetMapping("/v1/portal/sys/file/detail")
    public ApiResponse<SysFile> detail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(fileService.detail(param.getId()));
    }

    /** 按 ID 列表查询。 */
    @PostMapping("/v1/portal/sys/file/list_by_ids")
    public ApiResponse<List<SysFile>> listByIds(@Valid @RequestBody IdsParam param) {
        return ApiResponse.ok(fileService.listByIds(param.getIds()));
    }

    /** 下载生成代码。 */
    @GetMapping("/v1/portal/sys/file/download")
    public ResponseEntity<Resource> download(@Valid @ModelAttribute IdParam param) {
        SysFile meta = fileService.detail(param.getId());
        Resource resource = fileService.download(param.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + meta.getOriginalName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    /** 获取文件访问 URL。 */
    @PostMapping("/v1/portal/sys/file/url")
    public ApiResponse<SysFileUrlResult> url(@Valid @RequestBody SysFileObjectNameParam param) {
        return ApiResponse.ok(fileService.url(param.getObjectName()));
    }

    /** 获取预签名 URL。 */
    @PostMapping("/v1/portal/sys/file/presigned_url")
    public ApiResponse<SysFileUrlResult> presignedUrl(@Valid @RequestBody SysFileObjectNameParam param) {
        return ApiResponse.ok(fileService.presignedUrl(param.getObjectName()));
    }
}
