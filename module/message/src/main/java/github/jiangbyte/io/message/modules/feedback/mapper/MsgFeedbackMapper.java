package github.jiangbyte.io.message.modules.feedback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * 反馈表 {@code msg_feedback} 的 MyBatis-Plus Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface MsgFeedbackMapper extends BaseMapper<MsgFeedback> {
}
