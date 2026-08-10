package github.jiangbyte.io.auth.modules.login.controller;

import github.jiangbyte.io.auth.modules.login.service.AuthService;
import github.jiangbyte.io.auth.modules.login.param.CancelAccountParam;
import github.jiangbyte.io.auth.modules.login.result.AuthOptionsResult;
import github.jiangbyte.io.auth.modules.login.result.CaptchaResult;
import github.jiangbyte.io.auth.modules.login.param.ForgotPasswordParam;
import github.jiangbyte.io.auth.modules.login.param.LoginParam;
import github.jiangbyte.io.auth.modules.login.result.LoginResult;
import github.jiangbyte.io.auth.modules.login.result.PasswordKeyResult;
import github.jiangbyte.io.auth.modules.login.param.RegisterParam;
import github.jiangbyte.io.auth.modules.login.result.RegisterResult;
import github.jiangbyte.io.auth.modules.login.param.ResetPasswordParam;
import github.jiangbyte.io.auth.modules.login.param.SendLoginCodeParam;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.core.enums.AccountType;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 * 门户端认证 API：登录选项、注册、登录/登出、刷新会话、找回密码与注销账号。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortalAuthController {

    private final AuthService authService;

    /** 获取门户登录页公开配置。 */
    @GetMapping("/v1/portal/public/auth-options")
    public ApiResponse<AuthOptionsResult> authOptions() {
        return ApiResponse.ok(authService.authOptions(AccountType.PORTAL));
    }

    /** 获取图形验证码。 */
    @GetMapping("/v1/portal/captcha")
    public ApiResponse<CaptchaResult> captcha(@RequestParam(value = "format", defaultValue = "svg") String format) {
        return ApiResponse.ok(authService.captcha(format));
    }

    /** 获取密码传输 RSA 公钥。 */
    @GetMapping("/v1/portal/password-key")
    public ApiResponse<PasswordKeyResult> passwordKey() {
        return ApiResponse.ok(authService.passwordKey());
    }

    /** 发送门户登录 OTP。 */
    @PostMapping("/v1/portal/send-login-code")
    @OperationAudit(resourceType = "auth", action = "send_login_code")
    public ApiResponse<Void> sendLoginCode(@Valid @RequestBody SendLoginCodeParam request) {
        authService.sendLoginCode(request, AccountType.PORTAL);
        return ApiResponse.ok();
    }

    /** 门户登录。 */
    @PostMapping("/v1/portal/login")
    @OperationAudit(resourceType = "auth", action = "login")
    public ApiResponse<LoginResult> login(@Valid @RequestBody LoginParam request) {
        request.setAccountType(AccountType.PORTAL);
        return ApiResponse.ok(authService.login(request));
    }

    /** 刷新门户会话 Token。 */
    @PostMapping("/v1/portal/auth/refresh")
    @OperationAudit(resourceType = "auth", action = "refresh")
    public ApiResponse<LoginResult> refresh() {
        return ApiResponse.ok(authService.refreshSession());
    }

    /** 门户用户注册。 */
    @PostMapping("/v1/portal/register")
    @OperationAudit(resourceType = "auth", action = "register")
    public ApiResponse<RegisterResult> register(@Valid @RequestBody RegisterParam request) {
        return ApiResponse.ok(authService.registerPortal(request));
    }

    /** 门户登出。 */
    @PostMapping("/v1/portal/logout")
    @OperationAudit(resourceType = "auth", action = "logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.ok();
    }

    /** 门户忘记密码（发重置邮件）。 */
    @PostMapping("/v1/portal/forgot-password")
    public ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgotPasswordParam request) {
        authService.forgotPassword(request, AccountType.PORTAL);
        return ApiResponse.ok();
    }

    /** 门户通过令牌重置密码。 */
    @PostMapping("/v1/portal/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordParam request) {
        authService.resetPassword(request, AccountType.PORTAL);
        return ApiResponse.ok();
    }

    /** 注销当前门户账号。 */
    @PostMapping("/v1/portal/cancel")
    public ApiResponse<Void> cancel(@RequestBody(required = false) CancelAccountParam request) {
        authService.cancelAccount(request == null ? new CancelAccountParam() : request);
        return ApiResponse.ok();
    }
}
