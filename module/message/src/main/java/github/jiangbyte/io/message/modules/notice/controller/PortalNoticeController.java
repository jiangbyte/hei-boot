package github.jiangbyte.io.message.modules.notice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.message.modules.notice.entity.MsgNotice;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticePageParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeReadParam;
import github.jiangbyte.io.message.modules.notice.service.NoticeService;
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
    @GetMapping("/v1/portal/message/notices/list")
    public ApiResponse<Page<MsgNotice>> list(@Valid @ModelAttribute MsgNoticePageParam param) {
        return ApiResponse.ok(noticeService.portalList(param));
    }

    /** 当前门户用户可见消息分页。 */
    @GetMapping("/v1/portal/message/notices/my-page")
    public ApiResponse<Page<MsgNotice>> myPage(@Valid @ModelAttribute MsgNoticePageParam param) {
        return ApiResponse.ok(noticeService.myPage(param));
    }

    /** 当前门户用户可见消息详情。 */
    @GetMapping("/v1/portal/message/notices/my-detail")
    public ApiResponse<MsgNotice> myDetail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(noticeService.myDetail(param.getId()));
    }

    /** 当前门户用户未读消息数。 */
    @GetMapping("/v1/portal/message/notices/unread-count")
    public ApiResponse<Integer> unreadCount() {
        return ApiResponse.ok(noticeService.unreadCount());
    }

    /** 标记指定消息为已读。 */
    @PostMapping("/v1/portal/message/notices/read")
    @OperationAudit(resourceType = "message_notice", action = "read")
    public ApiResponse<Void> read(@Valid @RequestBody MsgNoticeReadParam param) {
        noticeService.markRead(param);
        return ApiResponse.ok();
    }

    /** 将全部可见消息标记为已读。 */
    @PostMapping("/v1/portal/message/notices/read-all")
    @OperationAudit(resourceType = "message_notice", action = "read_all")
    public ApiResponse<Void> readAll() {
        noticeService.markAllRead();
        return ApiResponse.ok();
    }
}
