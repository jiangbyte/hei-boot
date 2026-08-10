package github.jiangbyte.io.auth.modules.login.provider;

import github.jiangbyte.io.auth.login.AuthApi;
import github.jiangbyte.io.auth.modules.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 跨模块 {@link AuthApi} 适配器，将用户中心等模块的改密/换绑请求委托给 {@link AuthService}。
 *
 * Author: Charlie
 */
@Service
@RequiredArgsConstructor
public class AuthApiProvider implements AuthApi {

    private final AuthService authService;

    @Override
    public void sendChangePasswordCode() {
        authService.sendChangePasswordCode();
    }

    @Override
    public void updateCurrentPassword(String passwordKeyId, String oldPassword, String newPassword, String otpCode) {
        authService.updateCurrentPassword(passwordKeyId, oldPassword, newPassword, otpCode);
    }

    @Override
    public void updateCurrentPhone(String passwordKeyId, String password, String phone, boolean phoneLoginEnabled) {
        authService.updateCurrentPhone(passwordKeyId, password, phone, phoneLoginEnabled);
    }

    @Override
    public void updateCurrentEmail(String passwordKeyId, String password, String email, boolean emailLoginEnabled) {
        authService.updateCurrentEmail(passwordKeyId, password, email, emailLoginEnabled);
    }
}
