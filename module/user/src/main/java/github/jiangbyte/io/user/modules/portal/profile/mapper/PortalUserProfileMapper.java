package github.jiangbyte.io.user.modules.portal.profile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.user.modules.portal.profile.entity.PortalUserProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 门户端用户资料表 {@code portal_user_profile} 的 MyBatis-Plus Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface PortalUserProfileMapper extends BaseMapper<PortalUserProfile> {
}
