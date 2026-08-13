package github.jiangbyte.io.message.modules.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.message.modules.notice.entity.MsgNotice;

import java.time.OffsetDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 公告/通知表 {@code msg_notice} 的 Mapper：基础 CRUD，以及未读统计与可见已发布 ID 查询。
 *
 * Author: Charlie
 */
@Mapper
public interface MsgNoticeMapper extends BaseMapper<MsgNotice> {

    /**
     * 统计对指定账号可见且未读的已发布消息数量。
     *
     * @param accountType 账户类型
     * @param accountId 账号 ID
     * @param kind 消息类型过滤，可空
     * @param now 当前时间（过期判断）
     */
    @Select("""
            <script>
            SELECT COUNT(1)
            FROM msg_notice n
            WHERE n.status = 'PUBLISHED'
              AND (n.kind &lt;&gt; 'ANNOUNCEMENT' OR n.expire_at IS NULL OR n.expire_at &gt; #{now})
              AND (
                    (
                        n.target_scope IN ('ALL', 'ACCOUNT_TYPE')
                        AND (
                            n.target_account_types IS NULL
                            OR jsonb_array_length(COALESCE(n.target_account_types::jsonb, '[]'::jsonb)) = 0
                            OR jsonb_exists(n.target_account_types::jsonb, #{accountType})
                        )
                    )
                    <if test="accountId != null and accountId != ''">
                    OR (
                        n.target_scope = 'SPECIFIC'
                        AND jsonb_exists(n.target_account_ids::jsonb, #{accountId})
                    )
                    </if>
              )
              AND NOT EXISTS (
                    SELECT 1
                    FROM msg_notice_read r
                    WHERE r.notice_id = n.id
                      AND r.account_type = #{accountType}
                      AND r.account_id = #{accountId}
              )
              <if test="kind != null and kind != ''">
              AND n.kind = #{kind}
              </if>
            </script>
            """)
    int countUnread(
            @Param("accountType") String accountType,
            @Param("accountId") String accountId,
            @Param("kind") String kind,
            @Param("now") OffsetDateTime now);

    /**
     * 列出对指定账号可见的已发布消息 ID。
     *
     * @param accountType 账户类型
     * @param accountId 账号 ID
     * @param kind 消息类型过滤，可空
     * @param now 当前时间（过期判断）
     */
    @Select("""
            <script>
            SELECT n.id
            FROM msg_notice n
            WHERE n.status = 'PUBLISHED'
              AND (n.kind &lt;&gt; 'ANNOUNCEMENT' OR n.expire_at IS NULL OR n.expire_at &gt; #{now})
              AND (
                    (
                        n.target_scope IN ('ALL', 'ACCOUNT_TYPE')
                        AND (
                            n.target_account_types IS NULL
                            OR jsonb_array_length(COALESCE(n.target_account_types::jsonb, '[]'::jsonb)) = 0
                            OR jsonb_exists(n.target_account_types::jsonb, #{accountType})
                        )
                    )
                    <if test="accountId != null and accountId != ''">
                    OR (
                        n.target_scope = 'SPECIFIC'
                        AND jsonb_exists(n.target_account_ids::jsonb, #{accountId})
                    )
                    </if>
              )
              <if test="kind != null and kind != ''">
              AND n.kind = #{kind}
              </if>
            ORDER BY n.id
            LIMIT #{limit} OFFSET #{offset}
            </script>
            """)
    List<String> listVisiblePublishedIdsPage(
            @Param("accountType") String accountType,
            @Param("accountId") String accountId,
            @Param("kind") String kind,
            @Param("now") OffsetDateTime now,
            @Param("offset") int offset,
            @Param("limit") int limit);

    @Select("""
            <script>
            SELECT n.id
            FROM msg_notice n
            WHERE n.status = 'PUBLISHED'
              AND (n.kind &lt;&gt; 'ANNOUNCEMENT' OR n.expire_at IS NULL OR n.expire_at &gt; #{now})
              AND (
                    (
                        n.target_scope IN ('ALL', 'ACCOUNT_TYPE')
                        AND (
                            n.target_account_types IS NULL
                            OR jsonb_array_length(COALESCE(n.target_account_types::jsonb, '[]'::jsonb)) = 0
                            OR jsonb_exists(n.target_account_types::jsonb, #{accountType})
                        )
                    )
                    <if test="accountId != null and accountId != ''">
                    OR (
                        n.target_scope = 'SPECIFIC'
                        AND jsonb_exists(n.target_account_ids::jsonb, #{accountId})
                    )
                    </if>
              )
              <if test="kind != null and kind != ''">
              AND n.kind = #{kind}
              </if>
            </script>
            """)
    List<String> listVisiblePublishedIds(
            @Param("accountType") String accountType,
            @Param("accountId") String accountId,
            @Param("kind") String kind,
            @Param("now") OffsetDateTime now);
}
