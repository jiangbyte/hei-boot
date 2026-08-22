package github.jiangbyte.io.profile.modules.identity.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 实名认证可选项：业务类型与可用通道。
 *
 * Author: Charlie
 */
@Data
public class RealNameCaseOptionsResult {

    private List<RealNameBusinessOptionResult> businessTypes = new ArrayList<>();
    private List<String> documentTypes = new ArrayList<>();

    @Data
    public static class RealNameBusinessOptionResult {
        private String businessType;
        private String label;
        private List<String> channels = new ArrayList<>();
    }
}
