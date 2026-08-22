package github.jiangbyte.io.profile.modules.identity.provider;

import github.jiangbyte.io.profile.ProfileIdentityApi;
import github.jiangbyte.io.profile.modules.identity.service.ProfileIdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 跨模块 {@link ProfileIdentityApi} 适配器。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class ProfileIdentityApiProvider implements ProfileIdentityApi {

    private final ProfileIdentityService profileIdentityService;

    @Override
    public Map<String, String> getVerifiedRealNames(Collection<String> accountIds) {
        return profileIdentityService.getVerifiedRealNames(accountIds);
    }

    @Override
    public Set<String> findAccountIdsByRealName(String name) {
        return profileIdentityService.findAccountIdsByRealName(name);
    }

    @Override
    public boolean isVerified(String accountId) {
        return profileIdentityService.isVerified(accountId);
    }
}
