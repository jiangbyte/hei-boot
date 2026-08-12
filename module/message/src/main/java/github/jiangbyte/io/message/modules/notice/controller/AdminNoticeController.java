package github.jiangbyte.io.message.modules.notice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.message.modules.notice.entity.MsgNotice;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeAddParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeEditParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticePageParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticePinParam;
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
 * 管理端公告/通知 API：创建编辑发布撤回置顶，以及当前用户消息列表、详情、未读与已读标记。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;

    /** 创建公告或通知。 */
    @PostMapping("/v1/admin/message/notices/create")
    @SaCheckPermission(value = "message:notice:create", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "create")
    public ApiResponse<Void> create(@Valid @RequestBody MsgNoticeAddParam param) {
        noticeService.create(param);
        return ApiResponse.ok();
    }

    /** 更新公告或通知。 */
    @PostMapping("/v1/admin/message/notices/update")
    @SaCheckPermission(value = "message:notice:update", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "update")
    public ApiResponse<Void> update(@Valid @RequestBody MsgNoticeEditParam param) {
        noticeService.update(param);
        return ApiResponse.ok();
    }

    /** 批量删除公告/通知。 */
    @PostMapping("/v1/admin/message/notices/delete")
    @SaCheckPermission(value = "message:notice:delete", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "delete")
    public ApiResponse<Void> delete(@Valid @RequestBody IdsParam param) {
        noticeService.delete(param);
        return ApiResponse.ok();
    }

    /** 查询消息详情（管理端）。 */
    @GetMapping("/v1/admin/message/notices/detail")
    @SaCheckPermission(value = "message:notice:detail", type = StpKit.TYPE_ADMIN)
    public ApiResponse<MsgNotice> detail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(noticeService.detail(param.getId()));
    }

    /** 分页查询消息（管理端）。 */
    @GetMapping("/v1/admin/message/notices/page")
    @SaCheckPermission(value = "message:notice:page", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<MsgNotice>> page(@Valid @ModelAttribute MsgNoticePageParam param) {
        return ApiResponse.ok(noticeService.page(param));
    }

    /** 批量发布消息。 */
    @PostMapping("/v1/admin/message/notices/publish")
    @SaCheckPermission(value = "message:notice:publish", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "publish")
    public ApiResponse<Void> publish(@Valid @RequestBody IdsParam param) {
        noticeService.publish(param);
        return ApiResponse.ok();
    }

    /** 批量撤回消息。 */
    @PostMapping("/v1/admin/message/notices/revoke")
    @SaCheckPermission(value = "message:notice:revoke", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "revoke")
    public ApiResponse<Void> revoke(@Valid @RequestBody IdsParam param) {
        noticeService.revoke(param);
        return ApiResponse.ok();
    }

    /** 设置公告置顶。 */
    @PostMapping("/v1/admin/message/notices/pin")
    @SaCheckPermission(value = "message:notice:pin", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "message_notice", action = "pin")
    public ApiResponse<Void> pin(@Valid @RequestBody MsgNoticePinParam param) {
        noticeService.pin(param);
        return ApiResponse.ok();
    }

    /** 当前用户可见消息分页。 */
    @GetMapping("/v1/admin/message/notices/my-page")
    public ApiResponse<Page<MsgNotice>> myPage(@Valid @ModelAttribute MsgNoticePageParam param) {
        return ApiResponse.ok(noticeService.myPage(param));
    }

    /** 当前用户可见消息详情。 */
    @GetMapping("/v1/admin/message/notices/my-detail")
    public ApiResponse<MsgNotice> myDetail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(noticeService.myDetail(param.getId()));
    }

    /** 当前用户未读消息数。 */
    @GetMapping("/v1/admin/message/notices/unread-count")
    public ApiResponse<Integer> unreadCount() {
        return ApiResponse.ok(noticeService.unreadCount());
    }

    /** 标记指定消息为已读。 */
    @PostMapping("/v1/admin/message/notices/read")
    @OperationAudit(resourceType = "message_notice", action = "read")
    public ApiResponse<Void> read(@Valid @RequestBody MsgNoticeReadParam param) {
        noticeService.markRead(param);
        return ApiResponse.ok();
    }

    /** 将全部可见消息标记为已读。 */
    @PostMapping("/v1/admin/message/notices/read-all")
    @OperationAudit(resourceType = "message_notice", action = "read_all")
    public ApiResponse<Void> readAll() {
        noticeService.markAllRead();
        return ApiResponse.ok();
    }
}
