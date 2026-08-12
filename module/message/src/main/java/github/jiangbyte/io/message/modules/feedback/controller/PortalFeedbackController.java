package github.jiangbyte.io.message.modules.feedback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.core.param.IdParam;
import github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackAddParam;
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
 * 门户端反馈 API：当前用户提交反馈并查看本人反馈列表与详情。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortalFeedbackController {

    private final FeedbackService feedbackService;

    /** 门户用户提交反馈。 */
    @PostMapping("/v1/portal/message/feedbacks/submit")
    @OperationAudit(resourceType = "message_feedback", action = "submit")
    public ApiResponse<Void> submit(@Valid @RequestBody MsgFeedbackAddParam param) {
        feedbackService.submit(param);
        return ApiResponse.ok();
    }

    /** 分页查询当前门户用户提交的反馈。 */
    @GetMapping("/v1/portal/message/feedbacks/my-page")
    public ApiResponse<Page<MsgFeedback>> myPage(@Valid @ModelAttribute MsgFeedbackPageParam param) {
        return ApiResponse.ok(feedbackService.myPage(param));
    }

    /** 查询当前门户用户本人的反馈详情。 */
    @GetMapping("/v1/portal/message/feedbacks/my-detail")
    public ApiResponse<MsgFeedback> myDetail(@Valid @ModelAttribute IdParam param) {
        return ApiResponse.ok(feedbackService.myDetail(param.getId()));
    }
}
