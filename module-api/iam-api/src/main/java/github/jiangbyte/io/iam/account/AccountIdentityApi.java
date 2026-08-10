package github.jiangbyte.io.iam.account;

/**
 * 窄化身份查询门面，供不得依赖 {@link AccountApi} 的调用方使用
 *（避免 Account ↔ UserProfile 的 Bean 循环依赖）。
 *
 * Author: Charlie
 */
public interface AccountIdentityApi {

    /** 判断账号是否已绑定指定类型身份。 */
    boolean hasIdentity(String accountId, String identityType);
}
