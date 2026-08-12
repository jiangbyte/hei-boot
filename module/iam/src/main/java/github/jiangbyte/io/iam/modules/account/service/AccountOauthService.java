package github.jiangbyte.io.iam.modules.account.service;

import github.jiangbyte.io.iam.modules.account.entity.SysAccountOauthBinding;

import java.util.List;

/**
 * 三方登录绑定领域服务。
 *
 * Author: Charlie
 */
public interface AccountOauthService {

    SysAccountOauthBinding findByProviderOpenId(String provider, String openId);

    SysAccountOauthBinding findByWechatUnionId(String unionId);

    List<SysAccountOauthBinding> listByAccount(String accountId);

    int countByAccount(String accountId);

    SysAccountOauthBinding upsertBinding(
            String accountId,
            String provider,
            String openId,
            String unionId,
            String nickname,
            String avatar,
            String rawProfileJson);

    void unbind(String accountId, String provider);

    void deleteByAccountIds(List<String> accountIds);
}
