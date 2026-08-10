package github.jiangbyte.io.user.modules.portal.profile.provider;

import github.jiangbyte.io.user.portal.profile.PortalUserProfileApi;
import github.jiangbyte.io.user.portal.profile.PortalUserProfileInfo;
import github.jiangbyte.io.user.modules.portal.profile.service.PortalUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 跨模块 {@link PortalUserProfileApi} 适配器，将 IAM/认证等模块的资料读写请求委托给 {@link PortalUserProfileService}。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class PortalUserProfileApiProvider implements PortalUserProfileApi {

    private final PortalUserProfileService portalUserProfileService;

    @Override
    public PortalUserProfileInfo getProfile(String accountId) {
        // 直接委托领域服务查询
        return portalUserProfileService.getProfile(accountId);
    }

    @Override
    public Map<String, PortalUserProfileInfo> getProfiles(Collection<String> accountIds) {
        return portalUserProfileService.getProfiles(accountIds);
    }

    @Override
    public Map<String, String> getDisplayNames(Collection<String> accountIds) {
        return portalUserProfileService.getDisplayNames(accountIds);
    }

    @Override
    public void upsertProfile(PortalUserProfileInfo info) {
        portalUserProfileService.upsertProfile(info);
    }

    @Override
    public void updatePhone(String accountId, String phone) {
        // API 方法名与领域服务命名对齐后转发
        portalUserProfileService.updatePhoneByAccount(accountId, phone);
    }

    @Override
    public void updateEmail(String accountId, String email) {
        portalUserProfileService.updateEmailByAccount(accountId, email);
    }

    @Override
    public void createProfile(String accountId, String name, String nickname, String email) {
        portalUserProfileService.createProfile(accountId, name, nickname, email);
    }

    @Override
    public void deleteProfiles(Collection<String> accountIds) {
        portalUserProfileService.deleteProfiles(accountIds);
    }

    @Override
    public Set<String> findAccountIdsByProfileFilters(String name, String phone, String email) {
        return portalUserProfileService.findAccountIdsByProfileFilters(name, phone, email);
    }
}
