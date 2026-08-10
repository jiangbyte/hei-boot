package github.jiangbyte.io.message.modules.feedback.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.common.satoken.StpKit;
import github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackAddParam;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackEditParam;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackPageParam;
import github.jiangbyte.io.message.modules.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端反馈 API：用户提交与查看本人反馈，以及管理员分页/详情/回复更新与删除。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminFeedbackController {

    private final FeedbackService feedbackService;

    /** 当前管理用户提交反馈。 */
    @PostMapping("/v1/admin/message/feedbacks/submit")
    public ApiResponse<Void> submit(@Valid @RequestBody MsgFeedbackAddParam param) {
        feedbackService.submit(param);
        return ApiResponse.ok();
    }

    /** 分页查询当前用户提交的反馈。 */
    @GetMapping("/v1/admin/message/feedbacks/my-page")
    public ApiResponse<Page<MsgFeedback>> myPage(@Valid @ModelAttribute MsgFeedbackPageParam param) {
        return ApiResponse.ok(feedbackService.myPage(param));
    }

    /** 查询当前用户本人的反馈详情。 */
    @GetMapping("/v1/admin/message/feedbacks/my-detail")
    public ApiResponse<MsgFeedback> myDetail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(feedbackService.myDetail(param.getId()));
    }

    /** 管理端分页查询全部反馈。 */
    @GetMapping("/v1/admin/message/feedbacks/page")
    @SaCheckPermission(value = "message:feedback:page", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<MsgFeedback>> page(@Valid @ModelAttribute MsgFeedbackPageParam param) {
        return ApiResponse.ok(feedbackService.page(param));
    }

    /** 管理端查询反馈详情。 */
    @GetMapping("/v1/admin/message/feedbacks/detail")
    @SaCheckPermission(value = "message:feedback:detail", type = StpKit.TYPE_ADMIN)
    public ApiResponse<MsgFeedback> detail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(feedbackService.detail(param.getId()));
    }

    /** 管理端更新反馈状态或回复。 */
    @PostMapping("/v1/admin/message/feedbacks/update")
    @SaCheckPermission(value = "message:feedback:update", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Void> update(@Valid @RequestBody MsgFeedbackEditParam param) {
        feedbackService.update(param);
        return ApiResponse.ok();
    }

    /** 管理端批量删除反馈。 */
    @PostMapping("/v1/admin/message/feedbacks/delete")
    @SaCheckPermission(value = "message:feedback:delete", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Void> delete(@Valid @RequestBody IdsParam param) {
        feedbackService.delete(param);
        return ApiResponse.ok();
    }
}
