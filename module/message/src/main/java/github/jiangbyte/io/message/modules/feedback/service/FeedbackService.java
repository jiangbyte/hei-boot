package github.jiangbyte.io.message.modules.feedback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackAddParam;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackEditParam;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackPageParam;

/**
 * 反馈领域服务：管理端分页与处理、用户提交与本人反馈查询，以及附件/提交人信息补全。
 *
 * Author: Charlie
 */
public interface FeedbackService extends IService<MsgFeedback> {

    /**
     * 管理端按状态/分类分页查询反馈，并补全附件与提交人信息。
     */
    Page<MsgFeedback> page(MsgFeedbackPageParam param);

    /**
     * 管理端按 ID 查询反馈详情（含补全信息）。
     */
    MsgFeedback detail(String id);

    /**
     * 管理端更新反馈状态与回复内容。
     */
    void update(MsgFeedbackEditParam param);

    /**
     * 按 ID 列表批量删除反馈。
     */
    void delete(IdsParam param);

    /**
     * 当前用户提交反馈（默认待处理，规范化附件对象名）。
     */
    void submit(MsgFeedbackAddParam param);

    /**
     * 分页查询当前用户提交的反馈。
     */
    Page<MsgFeedback> myPage(MsgFeedbackPageParam param);

    /**
     * 查询当前用户本人的反馈详情；非本人返回无权。
     */
    MsgFeedback myDetail(String id);

}
