package github.jiangbyte.io.auth.modules.login.service;

import github.jiangbyte.io.auth.modules.login.param.CancelAccountParam;
import github.jiangbyte.io.auth.modules.login.result.AuthOptionsResult;
import github.jiangbyte.io.auth.modules.login.result.CaptchaResult;
import github.jiangbyte.io.auth.modules.login.result.CurrentUserResult;
import github.jiangbyte.io.auth.modules.login.param.ForgotPasswordParam;
import github.jiangbyte.io.auth.modules.login.param.LoginParam;
import github.jiangbyte.io.auth.modules.login.result.LoginResult;
import github.jiangbyte.io.auth.modules.login.result.PasswordKeyResult;
import github.jiangbyte.io.auth.modules.login.param.RegisterParam;
import github.jiangbyte.io.auth.modules.login.result.RegisterResult;
import github.jiangbyte.io.auth.modules.login.param.ResetPasswordParam;
import github.jiangbyte.io.auth.modules.login.param.SendLoginCodeParam;
import github.jiangbyte.io.auth.modules.login.convert.AuthConvert;
import github.jiangbyte.io.auth.modules.login.support.AuthCryptoService;
import github.jiangbyte.io.auth.modules.login.support.LoginProtectionService;
import github.jiangbyte.io.common.core.enums.AccountType;
import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.notify.mail.MailSenderFacade;
import github.jiangbyte.io.common.notify.sms.SmsSenderFacade;
import cn.dev33.satoken.stp.StpLogic;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import github.jiangbyte.io.iam.account.AccountApi;
import github.jiangbyte.io.iam.account.AccountAuthorizationInfo;
import github.jiangbyte.io.iam.account.AccountInfo;
import github.jiangbyte.io.iam.account.PermissionGrantInfo;
import github.jiangbyte.io.iam.password.PasswordPolicyApi;
import github.jiangbyte.io.sys.config.ConfigApi;
import github.jiangbyte.io.user.admin.profile.AdminUserProfileApi;
import github.jiangbyte.io.user.portal.profile.PortalUserProfileApi;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * {@link AuthService} 实现：整合验证码/加解密、账号 IAM、登录保护、通知发送与 Sa-Token 会话签发。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Duration DEFAULT_OTP_TTL = Duration.ofMinutes(5);

    private final AuthCryptoService cryptoService;
    private final AccountApi accountApi;
    private final AdminUserProfileApi adminUserProfileApi;
    private final PortalUserProfileApi portalUserProfileApi;
    private final MailSenderFacade mailSenderFacade;
    private final SmsSenderFacade smsSenderFacade;
    private final PasswordPolicyApi passwordPolicyApi;
    private final LoginProtectionService loginProtectionService;
    private final ConfigApi configApi;
    private final AuthConvert authConvert;

    @Override
    public CaptchaResult captcha(String format) {
        return cryptoService.createCaptcha(format == null ? "svg" : format);
    }

    @Override
    public PasswordKeyResult passwordKey() {
        return cryptoService.createPasswordKey();
    }

    @Override
    public AuthOptionsResult authOptions(AccountType accountType) {
        AccountType type = accountType == null ? AccountType.ADMIN : accountType;
        String typeName = type.name();
        AuthOptionsResult result = new AuthOptionsResult();
        result.setAccountType(type);
        result.setAllowAccount(true);
        // 按账号类型读取登录/注册相关开关
        result.setAllowEmail(configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_EMAIL", true));
        result.setAllowPhone(configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_PHONE", true));
        result.setAllowOtp(configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_OTP", true));
        boolean defaultRegister = type == AccountType.PORTAL;
        result.setRegisterEnabled(configApi.getBoolean("AUTH_REGISTER_" + typeName + "_ENABLED", defaultRegister));
        result.setRegisterRequirePhone(configApi.getBoolean("AUTH_REGISTER_" + typeName + "_REQUIRE_PHONE", false));
        result.setRegisterRequireEmail(configApi.getBoolean(
                "AUTH_REGISTER_" + typeName + "_REQUIRE_EMAIL", type == AccountType.PORTAL));
        result.setPasswordChangeVerifyMethod(
                configApi.getValue("PASSWORD_CHANGE_VERIFY_METHOD", "OLD_PASSWORD").trim().toUpperCase(Locale.ROOT));
        result.setCopyrightText(nullToEmpty(configApi.getValue("COPYRIGHT_TEXT", "")).trim());
        result.setCopyrightUrl(nullToEmpty(configApi.getValue("COPYRIGHT_URL", "")).trim());
        return result;
    }

    @Override
    public void sendLoginCode(SendLoginCodeParam request, AccountType accountType) {
        cryptoService.verifyCaptcha(request.getCaptchaId(), request.getCaptchaValue());
        AccountType type = accountType == null ? AccountType.ADMIN : accountType;
        String channel = normalizeChannel(request.getChannel());
        String identityType = "EMAIL".equals(channel) ? "EMAIL" : "PHONE";
        ensureIdentityAllowed(type, identityType, "OTP");
        String normalized = normalizeTarget(channel, request.getTarget());
        AccountInfo account = accountApi.findByIdentifier(normalized, identityType);
        if (account == null) {
            // 非 AUTO_CREATE 时静默返回，避免枚举账号
            String policy = noUserPolicy(type, identityType);
            if (!"AUTO_CREATE".equals(policy)) {
                return;
            }
        }
        String code = sixDigitCode();
        Duration ttl = otpTtl();
        cryptoService.storeLoginOtp(type.name(), channel, normalized, code, ttl);
        sendCodeMailOrSms(channel, normalized, "LOGIN_CODE", code, ttl);
    }

    @Override
    public LoginResult login(LoginParam request) {
        cryptoService.verifyCaptcha(request.getCaptchaId(), request.getCaptchaValue());
        AccountType accountType = request.getAccountType() == null ? AccountType.ADMIN : request.getAccountType();
        String identityType = StringUtils.hasText(request.getIdentityType()) ? request.getIdentityType() : "ACCOUNT";
        String loginMode = StringUtils.hasText(request.getLoginMode())
                ? request.getLoginMode().trim().toUpperCase(Locale.ROOT)
                : "PASSWORD";
        String clientIp = currentClientIp();
        // 登录前检查账号/IP 是否被锁定
        loginProtectionService.ensureAllowed(accountType, request.getAccount(), clientIp);
        ensureIdentityAllowed(accountType, identityType, loginMode);

        try {
            AccountInfo account;
            if ("OTP".equals(loginMode)) {
                // OTP 登录：消费验证码后按邮箱/手机查账号
                verifyLoginOtp(request, accountType, identityType);
                account = accountApi.findByIdentifier(
                        normalizeLoginAccount(identityType, request.getAccount()), identityType);
                if (account == null
                        || !"ENABLED".equalsIgnoreCase(account.getAccountStatus())
                        || !accountType.name().equalsIgnoreCase(account.getAccountType())) {
                    throw new BizException(401, "Invalid account or password");
                }
            } else {
                // 密码登录：解密传输密文并校验哈希
                if (!StringUtils.hasText(request.getPassword()) || !StringUtils.hasText(request.getPasswordKeyId())) {
                    throw new BizException("Password is required");
                }
                String rawPassword = cryptoService.decryptPassword(request.getPasswordKeyId(), request.getPassword());
                account = accountApi.findByIdentifier(request.getAccount(), identityType);
                if (account == null
                        || !"ENABLED".equalsIgnoreCase(account.getAccountStatus())
                        || !accountApi.matchesPassword(rawPassword, account.getPasswordHash())) {
                    throw new BizException(401, "Invalid account or password");
                }
                if (!accountType.name().equalsIgnoreCase(account.getAccountType())) {
                    throw new BizException(401, "Account type mismatch");
                }
            }
            LoginResult result = issueSession(account, accountType, request.getRememberMe(), request.getAccount());
            loginProtectionService.recordSuccess(accountType, request.getAccount(), clientIp);
            return result;
        } catch (BizException ex) {
            loginProtectionService.recordFailure(accountType, request.getAccount(), clientIp);
            throw ex;
        }
    }

    @Override
    @Transactional
    public RegisterResult registerPortal(RegisterParam request) {
        if (!configApi.getBoolean("AUTH_REGISTER_PORTAL_ENABLED", true)) {
            throw new BizException("Portal registration is disabled");
        }
        cryptoService.verifyCaptcha(request.getCaptchaId(), request.getCaptchaValue());
        String email = StringUtils.hasText(request.getEmail())
                ? request.getEmail().trim().toLowerCase(Locale.ROOT)
                : null;
        String phone = StringUtils.hasText(request.getPhone()) ? request.getPhone().trim() : null;
        if (configApi.getBoolean("AUTH_REGISTER_PORTAL_REQUIRE_EMAIL", true) && !StringUtils.hasText(email)) {
            throw new BizException("Email is required for registration");
        }
        if (configApi.getBoolean("AUTH_REGISTER_PORTAL_REQUIRE_PHONE", false) && !StringUtils.hasText(phone)) {
            throw new BizException("Phone is required for registration");
        }

        String rawPassword = cryptoService.decryptPassword(request.getPasswordKeyId(), request.getPassword());
        passwordPolicyApi.assertValid(rawPassword, null, request.getAccount(), email, phone);

        AccountInfo existing = accountApi.findByIdentifier(request.getAccount(), "ACCOUNT");
        if (existing != null) {
            throw new BizException("Account already exists");
        }

        // 创建门户账号与可选手机身份
        AccountInfo account = accountApi.createPortalAccount(
                request.getAccount(),
                email,
                accountApi.encodePassword(rawPassword));
        if (StringUtils.hasText(phone)) {
            accountApi.upsertIdentity(account.getId(), "PHONE", phone, true);
        }

        String nickname = StringUtils.hasText(request.getNickname())
                ? request.getNickname()
                : "user-" + account.getId().substring(Math.max(0, account.getId().length() - 8));
        portalUserProfileApi.createProfile(account.getId(), request.getName(), nickname, email);
        accountApi.recordPasswordHistory(account.getId(), rawPassword, account.getId(), "register");

        // 按配置挂接默认角色/部门
        String roleId = configApi.getValue("AUTH_REGISTER_PORTAL_DEFAULT_ROLE_ID", "").trim();
        if (StringUtils.hasText(roleId)) {
            accountApi.assignRole(account.getId(), roleId);
        }
        String deptId = configApi.getValue("AUTH_REGISTER_PORTAL_DEFAULT_DEPT_ID", "").trim();
        if (StringUtils.hasText(deptId)) {
            accountApi.assignPrimaryDept(account.getId(), deptId);
        }

        if (StringUtils.hasText(email)) {
            try {
                mailSenderFacade.sendTemplated("REGISTER_SUCCESS", email, Map.of(
                        "app_name", configApi.getValue("APP_NAME", "HEI"),
                        "account", request.getAccount()));
            } catch (Exception ignored) {
                // 尽力发送生命周期邮件
            }
        }

        return authConvert.toRegisterResponse(account.getId(), request.getAccount(), AccountType.PORTAL);
    }

    @Override
    public void forgotPassword(ForgotPasswordParam request, AccountType accountType) {
        cryptoService.verifyCaptcha(request.getCaptchaId(), request.getCaptchaValue());
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        AccountInfo account = accountApi.findByIdentifier(email, "EMAIL");
        // 账号不存在或类型不匹配时静默返回，避免枚举
        if (account == null || !accountType.name().equalsIgnoreCase(account.getAccountType())) {
            return;
        }
        long ttlSeconds = Math.max(60L, configApi.getLong("AUTH_PASSWORD_RESET_TOKEN_TTL_SECONDS", 600));
        String token = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
        cryptoService.storeResetToken(token, account.getId(), Duration.ofSeconds(ttlSeconds));
        String resetLink = buildPasswordResetLink(token, accountType);
        Map<String, Object> vars = new LinkedHashMap<>();
        vars.put("app_name", configApi.getValue("COPYRIGHT_TEXT", "HEI"));
        vars.put("reset_link", resetLink);
        vars.put("email", email);
        vars.put("expire_minutes", String.valueOf(Math.max(1, ttlSeconds / 60)));
        mailSenderFacade.sendTemplated("RESET_PASSWORD_CODE", email, vars);
    }

    private String buildPasswordResetLink(String token, AccountType accountType) {
        AccountType type = accountType == null ? AccountType.ADMIN : accountType;
        String key = "AUTH_PASSWORD_RESET_URL_" + type.name();
        String base = configApi.getValue(key, "").trim();
        if (!StringUtils.hasText(base)) {
            throw new BizException("Missing sys_config: " + key);
        }
        String separator = base.contains("?") ? "&" : "?";
        return base + separator + "token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordParam request, AccountType accountType) {
        cryptoService.verifyCaptcha(request.getCaptchaId(), request.getCaptchaValue());
        String rawPassword = cryptoService.decryptPassword(request.getPasswordKeyId(), request.getPassword());
        // 消费一次性重置令牌
        String accountId = cryptoService.consumeResetToken(request.getToken());
        if (!StringUtils.hasText(accountId)) {
            throw new BizException("Invalid or expired reset token");
        }
        AccountInfo account = accountApi.getById(accountId);
        if (account == null || !accountType.name().equalsIgnoreCase(account.getAccountType())) {
            throw new BizException("Invalid reset token");
        }
        String accountName = accountApi.findIdentifier(accountId, "ACCOUNT");
        String email = accountApi.findIdentifier(accountId, "EMAIL");
        String phone = accountApi.findIdentifier(accountId, "PHONE");
        passwordPolicyApi.assertValid(rawPassword, accountId, accountName, email, phone);
        accountApi.updatePasswordHash(accountId, accountApi.encodePassword(rawPassword));
        accountApi.recordPasswordHistory(accountId, rawPassword, accountId, "self_reset");
    }

    @Override
    public CurrentUserResult currentUser() {
        return LoginHelper.currentUser()
                .map(authConvert::toCurrentUser)
                .orElseThrow(() -> new BizException(401, "未登录"));
    }

    @Override
    public void logout() {
        LoginHelper.currentUser().ifPresent(user -> LoginHelper.logout(user.getAccountType()));
    }

    @Override
    public LoginResult refreshSession() {
        LoginUser user = LoginHelper.requireUser();
        StpLogic logic = LoginHelper.stpLogic(user.getAccountType());
        // 优先使用配置的 TTL 续期，否则沿用当前 Token 超时
        long renewTtl = configApi.getLong("AUTH_TOKEN_TTL_SECONDS", 0);
        if (renewTtl <= 0) {
            renewTtl = logic.getTokenTimeout();
        }
        if (renewTtl > 0) {
            logic.renewTimeout(renewTtl);
        }
        LoginResult response = authConvert.toLoginResponse(
                user.getAccountId(), user.getAccountType(), user.isPasswordExpired());
        response.setToken(logic.getTokenValue());
        response.setExpiresIn(logic.getTokenTimeout());
        response.setPasswordExpiryWarningDays(passwordExpiryWarningDays(user.getAccountId()));
        return response;
    }

    @Override
    @Transactional
    public void cancelAccount(CancelAccountParam request) {
        LoginUser loginUser = LoginHelper.currentUser()
                .orElseThrow(() -> new BizException(401, "未登录"));
        AccountInfo account = accountApi.getById(loginUser.getAccountId());
        if (account == null) {
            throw new BizException(404, "Account not found");
        }
        // 标记注销后立即踢出当前会话
        accountApi.cancelAccount(
                loginUser.getAccountId(),
                loginUser.getAccountId(),
                request == null ? null : request.getCancelReason());
        LoginHelper.logout(loginUser.getAccountType());
    }

    @Override
    public void sendChangePasswordCode() {
        LoginUser loginUser = LoginHelper.requireUser();
        // 解析改密校验方式与联系人
        String method = changeVerifyMethod();
        if (!"EMAIL_CODE".equals(method) && !"PHONE_CODE".equals(method)) {
            throw new BizException("Current password change method does not use verification code");
        }
        String identityType = "EMAIL_CODE".equals(method) ? "EMAIL" : "PHONE";
        String channel = "EMAIL_CODE".equals(method) ? "EMAIL" : "PHONE";
        String target = accountApi.findIdentifier(loginUser.getAccountId(), identityType);
        if (!StringUtils.hasText(target)) {
            throw new BizException("Account has no bound contact for verification");
        }
        // 生成 OTP 写入缓存
        String code = sixDigitCode();
        Duration ttl = otpTtl();
        cryptoService.storeChangePasswordOtp(loginUser.getAccountType().name(), channel, loginUser.getAccountId(), code, ttl);
        // 发送邮件或短信
        sendCodeMailOrSms(channel, target, "CHANGE_PASSWORD_CODE", code, ttl);
    }

    @Transactional
    @Override
    public void updateCurrentPassword(String passwordKeyId, String oldPassword, String newPassword, String otpCode) {
        LoginUser loginUser = LoginHelper.requireUser();
        // 解密旧/新密码
        String[] decrypted = cryptoService.decryptPasswords(passwordKeyId, oldPassword, newPassword);
        String rawOld = decrypted[0];
        String rawNew = decrypted[1];
        if (!StringUtils.hasText(rawNew)) {
            throw new BizException("New password is required");
        }
        // 加载账号并校验旧密码或 OTP
        AccountInfo account = accountApi.getById(loginUser.getAccountId());
        if (account == null) {
            throw new BizException(404, "Account not found");
        }
        verifyChangePassword(account, loginUser.getAccountType(), rawOld, otpCode);
        // 校验密码策略后更新哈希与历史
        String accountName = accountApi.findIdentifier(account.getId(), "ACCOUNT");
        String email = accountApi.findIdentifier(account.getId(), "EMAIL");
        String phone = accountApi.findIdentifier(account.getId(), "PHONE");
        passwordPolicyApi.assertValid(rawNew, account.getId(), accountName, email, phone);
        accountApi.updatePasswordHash(account.getId(), accountApi.encodePassword(rawNew));
        accountApi.recordPasswordHistory(account.getId(), rawNew, account.getId(), "self_update");
    }

    @Transactional
    @Override
    public void updateCurrentPhone(String passwordKeyId, String password, String phone, boolean phoneLoginEnabled) {
        LoginUser loginUser = LoginHelper.requireUser();
        // 解密并校验登录密码
        String rawPassword = cryptoService.decryptPassword(passwordKeyId, password);
        AccountInfo account = accountApi.getById(loginUser.getAccountId());
        if (account == null) {
            throw new BizException(404, "Account not found");
        }
        if (!accountApi.matchesPassword(rawPassword, account.getPasswordHash())) {
            throw new BizException("Invalid password");
        }
        if (phoneLoginEnabled && !StringUtils.hasText(phone)) {
            throw new BizException("Phone login requires a phone");
        }
        // 更新手机身份
        accountApi.upsertIdentity(account.getId(), "PHONE", phone, phoneLoginEnabled);
        // 同步档案手机号
        if (loginUser.getAccountType() == AccountType.ADMIN) {
            adminUserProfileApi.updatePhone(loginUser.getAccountId(), phone);
        } else {
            portalUserProfileApi.updatePhone(loginUser.getAccountId(), phone);
        }
    }

    @Transactional
    @Override
    public void updateCurrentEmail(String passwordKeyId, String password, String email, boolean emailLoginEnabled) {
        LoginUser loginUser = LoginHelper.requireUser();
        // 解密并校验登录密码
        String rawPassword = cryptoService.decryptPassword(passwordKeyId, password);
        AccountInfo account = accountApi.getById(loginUser.getAccountId());
        if (account == null) {
            throw new BizException(404, "Account not found");
        }
        if (!accountApi.matchesPassword(rawPassword, account.getPasswordHash())) {
            throw new BizException("Invalid password");
        }
        if (emailLoginEnabled && !StringUtils.hasText(email)) {
            throw new BizException("Email login requires an email");
        }
        // 更新邮箱身份
        String normalized = StringUtils.hasText(email) ? email.trim().toLowerCase(Locale.ROOT) : null;
        accountApi.upsertIdentity(account.getId(), "EMAIL", normalized, emailLoginEnabled);
        // 同步档案邮箱
        if (loginUser.getAccountType() == AccountType.ADMIN) {
            adminUserProfileApi.updateEmail(loginUser.getAccountId(), normalized);
        } else {
            portalUserProfileApi.updateEmail(loginUser.getAccountId(), normalized);
        }
    }

    private LoginResult issueSession(AccountInfo account, AccountType accountType, Boolean rememberMe, String loginAccount) {
        // 加载授权快照并组装 LoginUser
        AccountAuthorizationInfo authorization = accountApi.getAuthorization(account.getId());
        int expireDays = configApi.getInt("PASSWORD_VALIDITY_DAYS", 90);
        boolean passwordExpired = accountApi.isPasswordExpired(account.getId(), expireDays);

        LoginUser loginUser = new LoginUser();
        loginUser.setAccountId(account.getId());
        loginUser.setAccount(loginAccount);
        loginUser.setAccountType(accountType);
        loginUser.setRoles(authorization.roleSet());
        loginUser.setPermissions(authorization.permissionSet());
        loginUser.setClientPermissions(authorization.clientPermissionSet());
        loginUser.setClientResources(authorization.clientResourceSet());
        loginUser.setRoleIds(authorization.getRoleIds());
        loginUser.setDeptIds(authorization.getDeptIds());
        loginUser.setGroupIds(authorization.getGroupIds());
        loginUser.setResourceIds(authorization.getResourceIds());
        loginUser.setButtonCodes(authorization.getButtonCodes());
        loginUser.setPermissionGrants(PermissionGrantInfo.toLoginGrants(authorization.getPermissionGrants()));
        loginUser.setRememberMe(rememberMe == null || rememberMe);
        loginUser.setPasswordExpired(passwordExpired);
        HttpServletRequest httpRequest = currentRequest();
        if (httpRequest != null) {
            loginUser.setClientIp(httpRequest.getRemoteAddr());
            loginUser.setUserAgent(httpRequest.getHeader("User-Agent"));
            loginUser.setDeviceLabel(deviceLabel(httpRequest.getHeader("User-Agent")));
        }
        LoginHelper.login(loginUser, resolveTokenTtlSeconds());

        accountApi.updateLoginMeta(
                account.getId(),
                loginUser.getClientIp(),
                OffsetDateTime.now(),
                loginUser.getDeviceLabel());

        LoginResult response = authConvert.toLoginResponse(account.getId(), accountType, passwordExpired);
        response.setToken(LoginHelper.stpLogic(accountType).getTokenValue());
        response.setExpiresIn(LoginHelper.stpLogic(accountType).getTokenTimeout());
        response.setPasswordExpiryWarningDays(passwordExpiryWarningDays(account.getId()));
        return response;
    }

    private Integer passwordExpiryWarningDays(String accountId) {
        int warning = configApi.getInt("PASSWORD_EXPIRY_WARNING_DAYS", 0);
        int expireDays = configApi.getInt("PASSWORD_VALIDITY_DAYS", 0);
        if (warning <= 0 || expireDays <= 0) {
            return null;
        }
        Integer age = accountApi.getPasswordAgeDays(accountId);
        if (age == null) {
            return null;
        }
        int remaining = expireDays - age;
        if (remaining > 0 && remaining <= warning) {
            return remaining;
        }
        return null;
    }

    private void verifyLoginOtp(LoginParam request, AccountType accountType, String identityType) {
        String code = request.getOtpCode() == null ? "" : request.getOtpCode().trim();
        if (!StringUtils.hasText(code)) {
            throw new BizException(401, "Invalid or expired OTP code");
        }
        String channel;
        if ("EMAIL".equalsIgnoreCase(identityType)) {
            channel = "EMAIL";
        } else if ("PHONE".equalsIgnoreCase(identityType)) {
            channel = "PHONE";
        } else {
            throw new BizException("OTP login requires email or phone");
        }
        String normalized = normalizeLoginAccount(identityType, request.getAccount());
        if (!cryptoService.consumeLoginOtp(accountType.name(), channel, normalized, code)) {
            throw new BizException(401, "Invalid or expired OTP code");
        }
    }

    private void verifyChangePassword(AccountInfo account, AccountType accountType, String oldPassword, String otpCode) {
        String method = changeVerifyMethod();
        if ("OLD_PASSWORD".equals(method)) {
            if (!StringUtils.hasText(oldPassword) || !accountApi.matchesPassword(oldPassword, account.getPasswordHash())) {
                throw new BizException("Old password is incorrect");
            }
            return;
        }
        if ("EMAIL_CODE".equals(method) || "PHONE_CODE".equals(method)) {
            if (!StringUtils.hasText(otpCode)) {
                throw new BizException("Verification code is required");
            }
            String channel = "EMAIL_CODE".equals(method) ? "EMAIL" : "PHONE";
            if (!cryptoService.consumeChangePasswordOtp(accountType.name(), channel, account.getId(), otpCode.trim())) {
                throw new BizException("Invalid or expired verification code");
            }
            return;
        }
        throw new BizException("Unsupported password change verify method: " + method);
    }

    private void ensureIdentityAllowed(AccountType accountType, String identityType, String loginMode) {
        String typeName = accountType.name();
        String mode = StringUtils.hasText(loginMode) ? loginMode.trim().toUpperCase(Locale.ROOT) : "PASSWORD";
        if ("OTP".equals(mode) && !configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_OTP", true)) {
            throw new BizException("OTP login is disabled");
        }
        if ("EMAIL".equalsIgnoreCase(identityType)
                && !configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_EMAIL", true)) {
            throw new BizException("Email login is disabled");
        }
        if ("PHONE".equalsIgnoreCase(identityType)
                && !configApi.getBoolean("AUTH_LOGIN_" + typeName + "_ALLOW_PHONE", true)) {
            throw new BizException("Phone login is disabled");
        }
        if ("ACCOUNT".equalsIgnoreCase(identityType) && "OTP".equals(mode)) {
            throw new BizException("OTP login requires email or phone");
        }
    }

    private String noUserPolicy(AccountType accountType, String identityType) {
        String typeName = accountType.name();
        if ("EMAIL".equalsIgnoreCase(identityType)) {
            return configApi.getValue("AUTH_LOGIN_" + typeName + "_EMAIL_NO_USER_POLICY", "DENY")
                    .trim()
                    .toUpperCase(Locale.ROOT);
        }
        if ("PHONE".equalsIgnoreCase(identityType)) {
            return configApi.getValue("AUTH_LOGIN_" + typeName + "_PHONE_NO_USER_POLICY", "DENY")
                    .trim()
                    .toUpperCase(Locale.ROOT);
        }
        return "DENY";
    }

    private void sendCodeMailOrSms(String channel, String target, String templateScene, String code, Duration ttl) {
        Map<String, Object> vars = new LinkedHashMap<>();
        vars.put("app_name", configApi.getValue("COPYRIGHT_TEXT", "HEI"));
        vars.put("code", code);
        vars.put("expire_minutes", String.valueOf(Math.max(1, ttl.toMinutes())));
        if ("EMAIL".equals(channel)) {
            mailSenderFacade.sendTemplated(templateScene, target, vars);
            return;
        }
        if ("PHONE".equals(channel)) {
            smsSenderFacade.sendTemplated(templateScene, target, vars);
            return;
        }
        throw new BizException("Unsupported channel");
    }

    private Duration otpTtl() {
        return DEFAULT_OTP_TTL;
    }

    private String changeVerifyMethod() {
        return configApi.getValue("PASSWORD_CHANGE_VERIFY_METHOD", "OLD_PASSWORD")
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    private static String sixDigitCode() {
        return String.format(Locale.ROOT, "%06d", SECURE_RANDOM.nextInt(1_000_000));
    }

    private static String normalizeChannel(String channel) {
        if (!StringUtils.hasText(channel)) {
            throw new BizException("Channel is required");
        }
        String value = channel.trim().toUpperCase(Locale.ROOT);
        if (!"EMAIL".equals(value) && !"PHONE".equals(value)) {
            throw new BizException("Unsupported channel");
        }
        return value;
    }

    private static String normalizeTarget(String channel, String target) {
        if (!StringUtils.hasText(target)) {
            throw new BizException("Target is required");
        }
        return "EMAIL".equals(channel) ? target.trim().toLowerCase(Locale.ROOT) : target.trim();
    }

    private static String normalizeLoginAccount(String identityType, String account) {
        if (!StringUtils.hasText(account)) {
            return account;
        }
        return "EMAIL".equalsIgnoreCase(identityType)
                ? account.trim().toLowerCase(Locale.ROOT)
                : account.trim();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static HttpServletRequest currentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    private static String currentClientIp() {
        HttpServletRequest request = currentRequest();
        return request == null ? null : request.getRemoteAddr();
    }

    private static String deviceLabel(String userAgent) {
        if (!StringUtils.hasText(userAgent)) {
            return null;
        }
        String value = userAgent.toLowerCase(Locale.ROOT);
        if (value.contains("mobile") || value.contains("android") || value.contains("iphone")) {
            return "Mobile";
        }
        if (value.contains("ipad") || value.contains("tablet")) {
            return "Tablet";
        }
        return "Desktop";
    }

    private long resolveTokenTtlSeconds() {
        long configured = configApi.getLong("AUTH_TOKEN_TTL_SECONDS", 0);
        return configured > 0 ? configured : -1;
    }
}
