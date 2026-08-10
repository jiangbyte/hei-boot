package github.jiangbyte.io.sys.modules.file.controller;

import github.jiangbyte.io.sys.modules.file.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 公开文件访问 API：本地存储直链下载。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicFileController {

    private final FileService fileService;

    /** 按查询参数下载文件。 */
    @GetMapping("/v1/files")
    public ResponseEntity<Resource> downloadByQuery(@RequestParam("object_name") String objectName) {

    /** 转换。 */
    return toResponse(objectName);
    }

    /** 按路径下载文件。 */
    @GetMapping("/v1/files/**")
    public ResponseEntity<Resource> downloadByPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String marker = "/v1/files/";
        int idx = uri.indexOf(marker);
        String objectName = idx >= 0 ? uri.substring(idx + marker.length()) : "";
        objectName = URLDecoder.decode(objectName, StandardCharsets.UTF_8).replace('\\', '/').replaceAll("^/+", "");

    /** 转换。 */
    return toResponse(objectName);
    }

    /** 转换。 */
    private ResponseEntity<Resource> toResponse(String objectName) {
        Resource resource = fileService.publicDownload(objectName);
        String filename = StringUtils.hasText(objectName) && objectName.contains("/")
                ? objectName.substring(objectName.lastIndexOf('/') + 1)
                : objectName;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
