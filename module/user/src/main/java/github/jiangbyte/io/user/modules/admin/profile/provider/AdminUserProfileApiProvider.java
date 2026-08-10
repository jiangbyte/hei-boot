package github.jiangbyte.io.user.modules.admin.profile.provider;

import github.jiangbyte.io.user.admin.profile.AdminUserProfileApi;
import github.jiangbyte.io.user.admin.profile.AdminUserProfileInfo;
import github.jiangbyte.io.user.modules.admin.profile.service.AdminUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 跨模块 {@link AdminUserProfileApi} 适配器，将 IAM/认证等模块的资料读写请求委托给 {@link AdminUserProfileService}。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AdminUserProfileApiProvider implements AdminUserProfileApi {

    private final AdminUserProfileService adminUserProfileService;

    @Override
    public AdminUserProfileInfo getProfile(String accountId) {
        // 直接委托领域服务查询
        return adminUserProfileService.getProfile(accountId);
    }

    @Override
    public Map<String, AdminUserProfileInfo> getProfiles(Collection<String> accountIds) {
        return adminUserProfileService.getProfiles(accountIds);
    }

    @Override
    public Map<String, String> getDisplayNames(Collection<String> accountIds) {
        return adminUserProfileService.getDisplayNames(accountIds);
    }

    @Override
    public void upsertProfile(AdminUserProfileInfo info) {
        adminUserProfileService.upsertProfile(info);
    }

    @Override
    public void updatePhone(String accountId, String phone) {
        // API 方法名与领域服务命名对齐后转发
        adminUserProfileService.updatePhoneByAccount(accountId, phone);
    }

    @Override
    public void updateEmail(String accountId, String email) {
        adminUserProfileService.updateEmailByAccount(accountId, email);
    }

    @Override
    public void createProfile(String accountId, String name, String nickname, String email) {
        adminUserProfileService.createProfile(accountId, name, nickname, email);
    }

    @Override
    public void deleteProfiles(Collection<String> accountIds) {
        adminUserProfileService.deleteProfiles(accountIds);
    }

    @Override
    public Set<String> findAccountIdsByProfileFilters(String name, String phone, String email) {
        return adminUserProfileService.findAccountIdsByProfileFilters(name, phone, email);
    }
}
