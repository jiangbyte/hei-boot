package github.jiangbyte.io.auth.modules.login.support;

import github.jiangbyte.io.auth.modules.login.result.CaptchaResult;
import github.jiangbyte.io.auth.modules.login.result.PasswordKeyResult;
import github.jiangbyte.io.common.core.exception.BizException;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * {@link AuthCryptoService} 实现：验证码哈希、RSA-OAEP 密码解密、重置令牌与 OTP 的 Redis 存储。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class AuthCryptoServiceImpl implements AuthCryptoService {

    private static final String CAPTCHA_ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final Duration CAPTCHA_TTL = Duration.ofMinutes(5);
    private static final Duration PASSWORD_KEY_TTL = Duration.ofMinutes(10);

    private final RedissonClient redissonClient;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public CaptchaResult createCaptcha(String format) {
        // 生成易读字符并缓存哈希（校验时一次性消费）
        StringBuilder value = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            value.append(CAPTCHA_ALPHABET.charAt(secureRandom.nextInt(CAPTCHA_ALPHABET.length())));
        }
        String captchaId = UUID.randomUUID().toString().replace("-", "");
        RBucket<String> bucket = redissonClient.getBucket(captchaKey(captchaId));
        bucket.set(passwordEncoder.encode(value.toString().toLowerCase()), CAPTCHA_TTL);

        String text = value.toString();
        CaptchaResult response = new CaptchaResult();
        response.setCaptchaId(captchaId);
        if ("png".equalsIgnoreCase(format)) {
            response.setImageType("image/png");
            response.setImageBase64(CaptchaImageRenderer.pngBase64(text, secureRandom));
        } else {
            response.setImageType("image/svg+xml");
            response.setImageBase64(CaptchaImageRenderer.svgBase64(text, secureRandom));
        }
        return response;
    }

    @Override
    public void verifyCaptcha(String captchaId, String captchaValue) {
        RBucket<String> bucket = redissonClient.getBucket(captchaKey(captchaId));
        // getAndDelete：一次性校验
        String hashed = bucket.getAndDelete();
        if (hashed == null || captchaValue == null
                || !passwordEncoder.matches(captchaValue.trim().toLowerCase(), hashed)) {
            throw new BizException("验证码无效或已过期");
        }
    }

    @Override
    public PasswordKeyResult createPasswordKey() {
        try {
            // 2048 位 RSA；私钥仅存 Redis，公钥返回客户端
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            String keyId = UUID.randomUUID().toString().replace("-", "");
            String privatePem = toPem(keyPair.getPrivate().getEncoded());
            redissonClient.getBucket(passwordKey(keyId)).set(privatePem, PASSWORD_KEY_TTL);
            PasswordKeyResult response = new PasswordKeyResult();
            response.setKeyId(keyId);
            response.setPublicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
            return response;
        } catch (Exception ex) {
            throw new BizException(500, "创建密码传输密钥失败");
        }
    }

    @Override
    public String decryptPassword(String passwordKeyId, String encryptedPassword) {
        return decryptPasswords(passwordKeyId, encryptedPassword)[0];
    }

    @Override
    public String[] decryptPasswords(String passwordKeyId, String... encryptedPasswords) {
        RBucket<String> bucket = redissonClient.getBucket(passwordKey(passwordKeyId));
        // 解密即消费私钥，防止重放
        String privatePem = bucket.getAndDelete();
        if (privatePem == null) {
            throw new BizException("密码加密密钥无效或已过期");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privatePem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""));
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey,
                    new javax.crypto.spec.OAEPParameterSpec(
                            "SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                            javax.crypto.spec.PSource.PSpecified.DEFAULT));
            String[] result = new String[encryptedPasswords.length];
            for (int i = 0; i < encryptedPasswords.length; i++) {
                String encryptedPassword = encryptedPasswords[i];
                if (encryptedPassword == null || encryptedPassword.isBlank()) {
                    result[i] = null;
                    continue;
                }
                byte[] encrypted = Base64.getDecoder().decode(encryptedPassword);
                result[i] = new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
            }
            return result;
        } catch (BizException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BizException("密码解密失败");
        }
    }

    @Override
    public void storeResetToken(String token, String accountId, Duration ttl) {
        redissonClient.getBucket(resetTokenKey(token)).set(accountId, ttl);
    }

    @Override
    public String consumeResetToken(String token) {
        return redissonClient.<String>getBucket(resetTokenKey(token)).getAndDelete();
    }

    @Override
    public void storeLoginOtp(String accountType, String channel, String target, String code, Duration ttl) {
        redissonClient.getBucket(loginOtpKey(accountType, channel, target)).set(code, ttl);
    }

    @Override
    public boolean consumeLoginOtp(String accountType, String channel, String target, String code) {
        RBucket<String> bucket = redissonClient.getBucket(loginOtpKey(accountType, channel, target));
        // 读取并比对 OTP
        String stored = bucket.get();
        if (stored == null || code == null || !stored.equals(code.trim())) {
            return false;
        }
        // 校验通过后删除
        bucket.delete();
        return true;
    }

    @Override
    public void storeChangePasswordOtp(String accountType, String channel, String accountId, String code, Duration ttl) {
        redissonClient.getBucket(changePasswordOtpKey(accountType, channel, accountId)).set(code, ttl);
    }

    @Override
    public boolean consumeChangePasswordOtp(String accountType, String channel, String accountId, String code) {
        RBucket<String> bucket = redissonClient.getBucket(changePasswordOtpKey(accountType, channel, accountId));
        // 读取并比对 OTP
        String stored = bucket.get();
        if (stored == null || code == null || !stored.equals(code.trim())) {
            return false;
        }
        // 校验通过后删除
        bucket.delete();
        return true;
    }

    @Override
    public void storeRegisterOtp(String channel, String target, String code, Duration ttl) {
        redissonClient.getBucket(registerOtpKey(channel, target)).set(code, ttl);
    }

    @Override
    public boolean consumeRegisterOtp(String channel, String target, String code) {
        RBucket<String> bucket = redissonClient.getBucket(registerOtpKey(channel, target));
        String stored = bucket.get();
        if (stored == null || code == null || !stored.equals(code.trim())) {
            return false;
        }
        bucket.delete();
        return true;
    }

    @Override
    public void storeBindOtp(String accountType, String channel, String accountId, String target, String code, Duration ttl) {
        redissonClient.getBucket(bindOtpKey(accountType, channel, accountId, target)).set(code, ttl);
    }

    @Override
    public boolean consumeBindOtp(String accountType, String channel, String accountId, String target, String code) {
        RBucket<String> bucket = redissonClient.getBucket(bindOtpKey(accountType, channel, accountId, target));
        String stored = bucket.get();
        if (stored == null || code == null || !stored.equals(code.trim())) {
            return false;
        }
        bucket.delete();
        return true;
    }

    private static String captchaKey(String captchaId) {
        return "captcha:" + captchaId;
    }

    private static String passwordKey(String keyId) {
        return "password:crypto:" + keyId;
    }

    private static String resetTokenKey(String token) {
        return "password:reset:" + token;
    }

    private static String loginOtpKey(String accountType, String channel, String target) {
        return "login:otp:" + accountType + ":" + channel + ":" + target;
    }

    private static String changePasswordOtpKey(String accountType, String channel, String accountId) {
        return "password:change:otp:" + accountType + ":" + channel + ":" + accountId;
    }

    private static String registerOtpKey(String channel, String target) {
        return "register:otp:" + channel + ":" + target;
    }

    private static String bindOtpKey(String accountType, String channel, String accountId, String target) {
        return "bind:otp:" + accountType + ":" + channel + ":" + accountId + ":" + target;
    }

    private static String toPem(byte[] encoded) {
        String body = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.UTF_8)).encodeToString(encoded);
        return "-----BEGIN PRIVATE KEY-----\n" + body + "\n-----END PRIVATE KEY-----";
    }

}
