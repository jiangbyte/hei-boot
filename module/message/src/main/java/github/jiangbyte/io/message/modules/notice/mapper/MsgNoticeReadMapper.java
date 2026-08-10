package github.jiangbyte.io.message.modules.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.message.modules.notice.entity.MsgNoticeRead;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 消息已读表 {@code msg_notice_read} 的 Mapper：基础 CRUD 与批量插入。
 *
 * Author: Charlie
 */
@Mapper
public interface MsgNoticeReadMapper extends BaseMapper<MsgNoticeRead> {

    /** 批量插入已读记录。 */
    @Insert("""
            <script>
            INSERT INTO msg_notice_read (id, notice_id, account_type, account_id, read_at)
            VALUES
            <foreach collection="list" item="item" separator=",">
              (#{item.id}, #{item.noticeId}, #{item.accountType}, #{item.accountId}, #{item.readAt})
            </foreach>
            </script>
            """)
    int insertBatch(@Param("list") List<MsgNoticeRead> list);
}
