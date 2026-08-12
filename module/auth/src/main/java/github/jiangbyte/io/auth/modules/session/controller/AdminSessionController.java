package github.jiangbyte.io.auth.modules.session.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import github.jiangbyte.io.common.satoken.StpKit;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpLogic;
import github.jiangbyte.io.auth.modules.session.param.SessionExitParam;
import github.jiangbyte.io.auth.modules.session.param.SessionPageParam;
import github.jiangbyte.io.auth.modules.session.param.SessionTokenExitParam;
import github.jiangbyte.io.auth.modules.session.result.SessionAccountResult;
import github.jiangbyte.io.auth.modules.session.result.SessionAnalysisResult;
import github.jiangbyte.io.auth.modules.session.result.SessionTokenResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.jiangbyte.io.common.core.domain.ApiResponse;
import github.jiangbyte.io.common.log.annotation.OperationAudit;
import github.jiangbyte.io.common.core.enums.AccountType;
import github.jiangbyte.io.common.satoken.model.LoginUser;
import github.jiangbyte.io.common.satoken.utils.LoginHelper;
import jakarta.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/**
 * 管理端在线会话 API：统计、分页查询、Token 列表及按账号/Token 强制下线。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
public class AdminSessionController {

    /** 汇总在线账号/Token 数量及近一小时新增等分析指标（轻量：不读 LoginUser）。 */
    @GetMapping("/v1/admin/auth/sessions/analysis")
    @SaCheckPermission(value = "auth:session:analysis", type = StpKit.TYPE_ADMIN)
    public ApiResponse<SessionAnalysisResult> analysis() {
        SessionAnalysisResult result = new SessionAnalysisResult();
        AnalysisAccumulator admin = analyzeLogic(StpKit.ADMIN);
        AnalysisAccumulator portal = analyzeLogic(StpKit.PORTAL);
        result.setOnlineAccountCount(admin.accountCount + portal.accountCount);
        result.setOnlineTokenCount(admin.tokenCount + portal.tokenCount);
        result.setAdminAccountCount(admin.accountCount);
        result.setPortalAccountCount(portal.accountCount);
        result.setOneHourNewCount(admin.oneHourNew + portal.oneHourNew);
        result.setMaxTokenCount(Math.max(admin.maxToken, portal.maxToken));
        return ApiResponse.ok(result);
    }

    /** 分页查询在线会话，支持账号类型、账号、IP、关键字过滤。 */
    @GetMapping("/v1/admin/auth/sessions/page")
    @SaCheckPermission(value = "auth:session:page", type = StpKit.TYPE_ADMIN)
    public ApiResponse<Page<SessionAccountResult>> page(@Valid @ModelAttribute SessionPageParam query) {
        List<SessionAccountResult> items = collectSessions(query.getAccountType());
        // 按查询条件内存过滤后再分页
        if (StringUtils.hasText(query.getAccountId())) {
            items = items.stream().filter(item -> query.getAccountId().equals(item.getAccountId())).toList();
        }
        if (StringUtils.hasText(query.getAccount())) {
            String keyword = query.getAccount().toLowerCase(Locale.ROOT);
            items = items.stream()
                    .filter(item -> containsIgnoreCase(item.getAccount(), keyword)
                            || containsIgnoreCase(item.getName(), keyword)
                            || containsIgnoreCase(item.getNickname(), keyword))
                    .toList();
        }
        if (StringUtils.hasText(query.getIp())) {
            String ip = query.getIp();
            items = items.stream()
                    .filter(item -> item.getTokens().stream()
                            .anyMatch(token -> token.getClientIp() != null && token.getClientIp().contains(ip)))
                    .toList();
        }
        if (StringUtils.hasText(query.getKeyword())) {
            String keyword = query.getKeyword().toLowerCase(Locale.ROOT);
            items = items.stream()
                    .filter(item -> containsIgnoreCase(item.getAccount(), keyword)
                            || containsIgnoreCase(item.getAccountId(), keyword))
                    .toList();
        }
        int from = Math.max(0, (query.getCurrent() - 1) * query.getSize());
        int to = Math.min(items.size(), from + query.getSize());
        List<SessionAccountResult> records = from >= items.size() ? List.of() : items.subList(from, to);
        Page<SessionAccountResult> page = new Page<>(query.getCurrent(), query.getSize(), items.size());
        page.setRecords(records);
        return ApiResponse.ok(page);
    }

    /** 列出指定账号下的全部 Token 会话。 */
    @GetMapping("/v1/admin/auth/sessions/tokens")
    @SaCheckPermission(value = "auth:session:tokenlist", type = StpKit.TYPE_ADMIN)
    public ApiResponse<List<SessionTokenResult>> tokens(
            @RequestParam String accountId,
            @RequestParam(required = false) String accountType) {
        return ApiResponse.ok(listTokens(resolveLogic(accountType), accountId, accountType).tokens());
    }

    /** 按账号批量强制下线（踢出全部 Token）。 */
    @PostMapping("/v1/admin/auth/sessions/exit")
    @SaCheckPermission(value = "auth:session:exit", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "auth_session", action = "exit")
    public ApiResponse<Void> exit(@Valid @RequestBody SessionExitParam request) {
        for (SessionExitParam.Target target : request.getTargets()) {
            resolveLogic(target.getAccountType()).logout(target.getAccountId());
        }
        return ApiResponse.ok();
    }

    /** 按 Token 值批量强制下线（同时尝试管理端与门户 StpLogic）。 */
    @PostMapping("/v1/admin/auth/sessions/token/exit")
    @SaCheckPermission(value = "auth:session:tokenexit", type = StpKit.TYPE_ADMIN)
    @OperationAudit(resourceType = "auth_session", action = "token_exit")
    public ApiResponse<Void> tokenExit(@Valid @RequestBody SessionTokenExitParam request) {
        LinkedHashSet<String> tokens = new LinkedHashSet<>(request.getTokens());
        for (String token : tokens) {
            if (!StringUtils.hasText(token)) {
                continue;
            }
            StpKit.ADMIN.logoutByTokenValue(token);
            StpKit.PORTAL.logoutByTokenValue(token);
        }
        return ApiResponse.ok();
    }

    /** 从 ADMIN/PORTAL StpLogic 收集在线账号会话。 */
    private List<SessionAccountResult> collectSessions(String accountType) {
        List<SessionAccountResult> items = new ArrayList<>();
        if (!StringUtils.hasText(accountType) || AccountType.ADMIN.name().equalsIgnoreCase(accountType)) {
            items.addAll(fromLogic(StpKit.ADMIN, AccountType.ADMIN.name()));
        }
        if (!StringUtils.hasText(accountType) || AccountType.PORTAL.name().equalsIgnoreCase(accountType)) {
            items.addAll(fromLogic(StpKit.PORTAL, AccountType.PORTAL.name()));
        }
        return items;
    }

    private List<SessionAccountResult> fromLogic(StpLogic stpLogic, String accountType) {
        List<String> sessionIds = stpLogic.searchSessionId("", 0, -1, false);
        List<SessionAccountResult> items = new ArrayList<>();
        for (String sessionId : sessionIds) {
            SaSession accountSession = stpLogic.getSessionBySessionId(sessionId);
            Object loginId = accountSession == null ? null : accountSession.getLoginId();
            if (loginId == null) {
                continue;
            }
            String accountId = String.valueOf(loginId);
            TokenBundle bundle = listTokens(stpLogic, accountId, accountType);
            List<SessionTokenResult> tokens = bundle.tokens();
            SessionAccountResult item = new SessionAccountResult();
            item.setAccountId(accountId);
            item.setAccountType(accountType);
            item.setTokenCount(tokens.size());
            item.setTokens(tokens);
            if (!tokens.isEmpty()) {
                SessionTokenResult latest = tokens.getFirst();
                item.setClientIp(latest.getClientIp());
                item.setDeviceLabel(latest.getDeviceLabel());
                item.setLatestLoginIp(latest.getClientIp());
                item.setLatestLoginTime(latest.getLoginAt());
                item.setLatestActiveAt(latest.getLastActiveAt() != null ? latest.getLastActiveAt() : latest.getLoginAt());
                item.setFirstLoginAt(tokens.stream()
                        .map(SessionTokenResult::getLoginAt)
                        .filter(StringUtils::hasText)
                        .min(String::compareTo)
                        .orElse(null));
            }
            String account = bundle.account();
            if (!StringUtils.hasText(account) && accountSession != null) {
                Object user = accountSession.get(LoginHelper.LOGIN_USER_KEY);
                if (user instanceof LoginUser loginUser && StringUtils.hasText(loginUser.getAccount())) {
                    account = loginUser.getAccount();
                }
            }
            item.setAccount(StringUtils.hasText(account) ? account : accountId);
            items.add(item);
        }
        return items;
    }

    /** 分析用轻量扫描：只读 session/token 元数据，不拉 LoginUser。 */
    private AnalysisAccumulator analyzeLogic(StpLogic stpLogic) {
        AnalysisAccumulator acc = new AnalysisAccumulator();
        OffsetDateTime oneHourAgo = OffsetDateTime.now(ZoneOffset.UTC).minusHours(1);
        List<String> sessionIds = stpLogic.searchSessionId("", 0, -1, false);
        for (String sessionId : sessionIds) {
            SaSession accountSession = stpLogic.getSessionBySessionId(sessionId);
            Object loginId = accountSession == null ? null : accountSession.getLoginId();
            if (loginId == null) {
                continue;
            }
            acc.accountCount++;
            List<String> tokenValues = stpLogic.getTokenValueListByLoginId(String.valueOf(loginId));
            int count = tokenValues == null ? 0 : tokenValues.size();
            acc.tokenCount += count;
            acc.maxToken = Math.max(acc.maxToken, count);
            if (tokenValues == null) {
                continue;
            }
            for (String token : tokenValues) {
                SaSession session = stpLogic.getTokenSessionByToken(token);
                if (session == null) {
                    continue;
                }
                OffsetDateTime loginAt = parseTime(formatEpoch(session.getCreateTime()));
                if (loginAt != null && !loginAt.isBefore(oneHourAgo)) {
                    acc.oneHourNew++;
                }
            }
        }
        return acc;
    }

    private TokenBundle listTokens(StpLogic stpLogic, String accountId, String accountType) {
        List<String> tokenValues = stpLogic.getTokenValueListByLoginId(accountId);
        List<SessionTokenResult> result = new ArrayList<>();
        String account = null;
        for (String token : tokenValues) {
            SessionTokenResult info = new SessionTokenResult();
            info.setToken(token);
            info.setAccountId(accountId);
            info.setAccountType(accountType);
            SaSession session = stpLogic.getTokenSessionByToken(token);
            if (session != null) {
                info.setLoginAt(formatEpoch(session.getCreateTime()));
                Object user = session.get(LoginHelper.LOGIN_USER_KEY);
                if (user instanceof LoginUser loginUser) {
                    info.setAccountType(loginUser.getAccountType() == null
                            ? accountType
                            : loginUser.getAccountType().name());
                    info.setClientIp(loginUser.getClientIp());
                    info.setDeviceLabel(loginUser.getDeviceLabel());
                    info.setUserAgent(loginUser.getUserAgent());
                    info.setRememberMe(loginUser.isRememberMe());
                    if (!StringUtils.hasText(account) && StringUtils.hasText(loginUser.getAccount())) {
                        account = loginUser.getAccount();
                    }
                }
            }
            long timeout = stpLogic.getTokenTimeout(token);
            if (timeout > 0) {
                info.setExpiresAt(OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(timeout).toString());
            }
            info.setLastActiveAt(info.getLoginAt());
            result.add(info);
        }
        result.sort((a, b) -> {
            String left = a.getLastActiveAt() != null ? a.getLastActiveAt() : "";
            String right = b.getLastActiveAt() != null ? b.getLastActiveAt() : "";
            return right.compareTo(left);
        });
        return new TokenBundle(result, account);
    }

    private StpLogic resolveLogic(String accountType) {
        if (AccountType.PORTAL.name().equalsIgnoreCase(accountType)) {
            return StpKit.PORTAL;
        }
        return StpKit.ADMIN;
    }

    private static boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }

    private static String formatEpoch(long epochMillis) {
        if (epochMillis <= 0) {
            return null;
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneOffset.UTC).toString();
    }

    private static OffsetDateTime parseTime(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return OffsetDateTime.parse(value);
        } catch (Exception ignored) {
            return null;
        }
    }

    private record TokenBundle(List<SessionTokenResult> tokens, String account) {
    }

    private static final class AnalysisAccumulator {
        private int accountCount;
        private int tokenCount;
        private int oneHourNew;
        private int maxToken;
    }
}
