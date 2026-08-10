package github.jiangbyte.io.auth.modules.session.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量强制下线账号会话的请求参数（按账号 ID + 账号类型）。
 *
 * Author: Charlie
 */
@Data
public class SessionExitParam {

    @NotEmpty
    @Valid
    private List<Target> targets = new ArrayList<>();

    /**
     * 单个强制下线目标。
     */
    @Data
    public static class Target {
        @NotBlank
        private String accountId;
        private String accountType;
    }
}
