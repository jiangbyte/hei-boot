package github.jiangbyte.io.auth;

/** Author: Charlie **/

import cn.dev33.satoken.stp.StpLogic;
import github.jiangbyte.io.auth.modules.login.convert.AuthConvert;
import github.jiangbyte.io.auth.modules.login.result.LoginResult;
import github.jiangbyte.io.auth.modules.login.service.AuthServiceImpl;
import github.jiangbyte.io.auth.modules.login.support.AuthCryptoService;
import github.jiangbyte.io.auth.modules.login.support.LoginProtectionService;
import github.jiangbyte.io.common.core.enums.AccountType;
import github.jiangbyte.io.common.notify.mail.MailSenderFacade;
import github.jiangbyte.io.common.notify.sms.SmsSenderFacade;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.iam.account.AccountApi;
import github.jiangbyte.io.iam.password.PasswordPolicyApi;
import github.jiangbyte.io.sys.config.ConfigApi;
import github.jiangbyte.io.user.admin.profile.AdminUserProfileApi;
import github.jiangbyte.io.user.portal.profile.PortalUserProfileApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceRefreshSessionTest {

    @Mock
    private AuthCryptoService cryptoService;
    @Mock
    private AccountApi accountApi;
    @Mock
    private AdminUserProfileApi adminUserProfileApi;
    @Mock
    private PortalUserProfileApi portalUserProfileApi;
    @Mock
    private MailSenderFacade mailSenderFacade;
    @Mock
    private SmsSenderFacade smsSenderFacade;
    @Mock
    private PasswordPolicyApi passwordPolicyApi;
    @Mock
    private LoginProtectionService loginProtectionService;
    @Mock
    private ConfigApi configApi;
    @Mock
    private AuthConvert authConvert;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        when(configApi.getLong("AUTH_TOKEN_TTL_SECONDS", 0)).thenReturn(3600L);
        when(configApi.getInt("PASSWORD_EXPIRY_WARNING_DAYS", 0)).thenReturn(0);
        when(configApi.getInt("PASSWORD_VALIDITY_DAYS", 0)).thenReturn(0);
        authService = new AuthServiceImpl(
                cryptoService,
                accountApi,
                adminUserProfileApi,
                portalUserProfileApi,
                mailSenderFacade,
                smsSenderFacade,
                passwordPolicyApi,
                loginProtectionService,
                configApi,
                authConvert);
    }

    @Test
    void refreshSessionRenewsWithConfiguredTtl() {
        LoginUser user = new LoginUser();
        user.setAccountId("1");
        user.setAccountType(AccountType.ADMIN);
        user.setPasswordExpired(false);

        LoginResult converted = new LoginResult();
        converted.setAccountId("1");
        converted.setAccountType(AccountType.ADMIN);

        when(authConvert.toLoginResponse(eq("1"), eq(AccountType.ADMIN), eq(false))).thenReturn(converted);

        StpLogic stpLogic = mock(StpLogic.class);
        when(stpLogic.getTokenValue()).thenReturn("renewed-token");
        when(stpLogic.getTokenTimeout()).thenReturn(3600L);

        try (MockedStatic<LoginHelper> loginHelper = mockStatic(LoginHelper.class)) {
            loginHelper.when(LoginHelper::requireUser).thenReturn(user);
            loginHelper.when(() -> LoginHelper.stpLogic(AccountType.ADMIN)).thenReturn(stpLogic);

            LoginResult response = authService.refreshSession();

            verify(stpLogic).renewTimeout(3600L);
            assertEquals("renewed-token", response.getToken());
            assertEquals(3600L, response.getExpiresIn());
        }
    }
}
