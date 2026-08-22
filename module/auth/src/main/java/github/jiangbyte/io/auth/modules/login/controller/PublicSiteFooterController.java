package github.jiangbyte.io.auth.modules.login.controller;

import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.sys.config.ConfigApi;
import github.jiangbyte.io.sys.config.SiteFooterConfig;
import github.jiangbyte.io.sys.config.SiteFooterResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点页脚公开接口：版权与备案，供 Admin / Portal 等前端统一拉取。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicSiteFooterController {

    private final ConfigApi configApi;

    @GetMapping("/site-footer")
    public ApiResponse<SiteFooterResult> siteFooter() {
        return ApiResponse.ok(SiteFooterConfig.resolve(configApi));
    }
}
