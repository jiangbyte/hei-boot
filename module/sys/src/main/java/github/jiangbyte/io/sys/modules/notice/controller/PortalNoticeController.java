package github.jiangbyte.io.sys.modules.notice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.sys.modules.notice.entity.SysNotice;
import github.jiangbyte.io.sys.modules.notice.param.SysNoticePageParam;
import github.jiangbyte.io.sys.modules.notice.param.SysNoticeReadParam;
import github.jiangbyte.io.sys.modules.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门户端公告/通知 API：公告列表、本人消息、详情、未读数与已读标记。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortalNoticeController {

    private final NoticeService noticeService;

    /** 门户公告列表（已发布可见）。 */
    @GetMapping("/v1/portal/sys/notices/list")
    public ApiResponse<Page<SysNotice>> list(@Valid @ModelAttribute SysNoticePageParam param) {
        return ApiResponse.ok(noticeService.portalList(param));
    }

    /** 当前门户用户可见消息分页。 */
    @GetMapping("/v1/portal/sys/notices/my-page")
    public ApiResponse<Page<SysNotice>> myPage(@Valid @ModelAttribute SysNoticePageParam param) {
        return ApiResponse.ok(noticeService.myPage(param));
    }

    /** 当前门户用户可见消息详情。 */
    @GetMapping("/v1/portal/sys/notices/my-detail")
    public ApiResponse<SysNotice> myDetail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(noticeService.myDetail(param.getId()));
    }

    /** 当前门户用户未读消息数。 */
    @GetMapping("/v1/portal/sys/notices/unread-count")
    public ApiResponse<Integer> unreadCount() {
        return ApiResponse.ok(noticeService.unreadCount());
    }

    /** 标记指定消息为已读。 */
    @PostMapping("/v1/portal/sys/notices/read")
    public ApiResponse<Void> read(@Valid @RequestBody SysNoticeReadParam param) {
        noticeService.markRead(param);
        return ApiResponse.ok();
    }

    /** 将全部可见消息标记为已读。 */
    @PostMapping("/v1/portal/sys/notices/read-all")
    public ApiResponse<Void> readAll() {
        noticeService.markAllRead();
        return ApiResponse.ok();
    }
}