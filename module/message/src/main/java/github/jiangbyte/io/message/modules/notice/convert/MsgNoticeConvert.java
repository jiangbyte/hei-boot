package github.jiangbyte.io.message.modules.notice.convert;

import github.jiangbyte.io.message.modules.notice.entity.MsgNotice;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeAddParam;
import github.jiangbyte.io.message.modules.notice.param.MsgNoticeEditParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * 公告/通知对象转换：新增/编辑参数与 {@link github.jiangbyte.io.message.modules.notice.entity.MsgNotice} 实体的 MapStruct 映射。
 *
 * Author: Charlie
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MsgNoticeConvert {

    /**
     * 将新增参数映射为消息实体。
     */
    MsgNotice toEntity(MsgNoticeAddParam param);

    /**
     * 将编辑参数覆盖到已有消息实体。
     */
    void update(MsgNoticeEditParam param, @MappingTarget MsgNotice entity);

}
