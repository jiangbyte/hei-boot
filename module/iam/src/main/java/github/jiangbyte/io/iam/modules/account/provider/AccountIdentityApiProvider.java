package github.jiangbyte.io.iam.modules.account.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import github.jiangbyte.io.iam.account.AccountIdentityApi;
import github.jiangbyte.io.iam.modules.account.entity.SysAccountIdentity;
import github.jiangbyte.io.iam.modules.account.mapper.SysAccountIdentityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 跨模块账号身份查询 API 适配器：判断账号是否具备某类身份标识。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AccountIdentityApiProvider implements AccountIdentityApi {

    private final SysAccountIdentityMapper identityMapper;

    @Override
    public boolean hasIdentity(String accountId, String identityType) {
        Long count = identityMapper.selectCount(Wrappers.<SysAccountIdentity>lambdaQuery()
                .eq(SysAccountIdentity::getAccountId, accountId)
                .eq(SysAccountIdentity::getIdentityType, identityType)
                .eq(SysAccountIdentity::getBindStatus, "BOUND"));
        return count != null && count > 0;
    }
}
