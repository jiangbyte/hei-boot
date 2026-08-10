package github.jiangbyte.io.message.modules.notice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import github.jiangbyte.io.common.core.param.IdsParam;
import github.jiangbyte.io.message.modules.notice.entity.MsgNotice;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeAddParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeEditParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticePageParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticePinParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeReadParam;

/**
 * 公告/通知领域服务：管理端 CRUD 与发布流转，以及用户侧可见列表、详情、未读与已读。
 *
 * Author: Charlie
 */
public interface NoticeService extends IService<MsgNotice> {

    /**
     * 创建公告或通知（校验类型/目标范围并落库）。
     */
    void create(MsgNoticeAddParam param);

    /**
     * 更新公告或通知内容与状态。
     */
    void update(MsgNoticeEditParam param);

    /**
     * 批量删除消息及其已读记录。
     */
    void delete(IdsParam param);

    /**
     * 管理端按 ID 查询消息详情。
     */
    MsgNotice detail(String id);

    /**
     * 管理端按标题/状态/类型分页查询。
     */
    Page<MsgNotice> page(MsgNoticePageParam param);

    /**
     * 批量发布消息并记录发布人与发布时间。
     */
    void publish(IdsParam param);

    /**
     * 批量撤回已发布消息。
     */
    void revoke(IdsParam param);

    /**
     * 设置公告置顶状态与置顶截止时间。
     */
    void pin(MsgNoticePinParam param);

    /**
     * 门户公告列表（已发布且对当前用户可见）。
     */
    Page<MsgNotice> portalList(MsgNoticePageParam param);

    /**
     * 当前用户可见的已发布消息分页（含已读标记）。
     */
    Page<MsgNotice> myPage(MsgNoticePageParam param);

    /**
     * 查看本人可见消息详情；公告累加浏览并标记已读。
     */
    MsgNotice myDetail(String id);

    /**
     * 统计当前用户未读消息数量。
     */
    int unreadCount();

    /**
     * 将指定消息标记为已读。
     */
    void markRead(MsgNoticeReadParam param);

    /**
     * 将当前用户可见的全部已发布消息标记为已读。
     */
    void markAllRead();
}
