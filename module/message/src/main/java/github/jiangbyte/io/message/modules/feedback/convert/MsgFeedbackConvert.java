package github.jiangbyte.io.message.modules.feedback.convert;

import github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback;
import github.jiangbyte.io.message.modules.feedback.param.MsgFeedbackAddParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 反馈对象转换：新增参数到 {@link github.jiangbyte.io.message.modules.feedback.entity.MsgFeedback} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MsgFeedbackConvert {

    /**
     * 将新增参数映射为反馈实体。
     */
    MsgFeedback toEntity(MsgFeedbackAddParam param);

}
