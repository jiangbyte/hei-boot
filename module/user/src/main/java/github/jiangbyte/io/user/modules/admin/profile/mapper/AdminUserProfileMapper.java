package github.jiangbyte.io.user.modules.admin.profile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import github.jiangbyte.io.user.modules.admin.profile.entity.AdminUserProfile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理端用户资料表 {@code admin_user_profile} 的 MyBatis-Plus Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface AdminUserProfileMapper extends BaseMapper<AdminUserProfile> {
}
