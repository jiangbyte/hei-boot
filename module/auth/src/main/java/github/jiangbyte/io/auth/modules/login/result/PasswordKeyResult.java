package github.jiangbyte.io.auth.modules.login.result;

import lombok.Data;

/**
 * 密码传输加密公钥响应：一次性 keyId 与 RSA 公钥（Base64）。
 *
 * Author: Charlie
 */
@Data
public class PasswordKeyResult {
    private String keyId;
    private String publicKey;
}
