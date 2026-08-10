package github.jiangbyte.io.common.security.ratelimit;

import github.jiangbyte.io.common.security.web.ClientIpResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 基于配置的 HTTP 限流过滤器（按 IP / 路径等维度计数）。
 *
 * Author: Charlie
 */
public class RateLimitFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    private final RedissonClient redissonClient;
    private final RateLimitProperties properties;
    private final List<CompiledRule> rules;

    public RateLimitFilter(RedissonClient redissonClient, RateLimitProperties properties) {
        this.redissonClient = redissonClient;
        this.properties = properties;
        this.rules = compile(properties.getRules());
    }

    /** 按配置对请求执行限流检查。 */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (!properties.isEnabled() || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        String path = request.getRequestURI();
        for (CompiledRule rule : rules) {
            if (!rule.pattern.matcher(path).find()) {
                continue;
            }
            String ip = ClientIpResolver.resolve(request, properties.isTrustForwardedHeaders());
            String key = "rl:ip:" + ip + ":" + rule.pattern.pattern();
            try {
                if (isLimited(key, rule.limit, rule.windowSeconds)) {
                    log.warn("Rate limit exceeded: {}", key);
                    response.setStatus(429);
                    response.setHeader("Retry-After", String.valueOf(rule.windowSeconds));
                    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(
                            "{\"code\":\"429\",\"message\":\"请求过于频繁，请稍后再试\",\"data\":null}");
                    return;
                }
            } catch (Exception ex) {
                log.debug("Rate limit check failed for {}", key, ex);
            }
            break;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isLimited(String key, int limit, int windowSeconds) {
        long now = System.currentTimeMillis();
        long windowStart = now - windowSeconds * 1000L;
        RScoredSortedSet<String> set = redissonClient.getScoredSortedSet(key);
        String member = now + ":" + UUID.randomUUID().toString().substring(0, 8);
        set.add(now, member);
        set.removeRangeByScore(0, true, windowStart, true);
        set.expire(Duration.ofSeconds(windowSeconds));
        return set.size() > limit;
    }

    private static List<CompiledRule> compile(List<RateLimitProperties.Rule> source) {
        List<CompiledRule> compiled = new ArrayList<>();
        if (source == null) {
            return compiled;
        }
        for (RateLimitProperties.Rule rule : source) {
            if (rule == null || !StringUtils.hasText(rule.getPathPattern())) {
                continue;
            }
            compiled.add(new CompiledRule(
                    Pattern.compile(rule.getPathPattern()),
                    Math.max(1, rule.getLimit()),
                    Math.max(1, rule.getWindowSeconds())));
        }
        return compiled;
    }

    private record CompiledRule(Pattern pattern, int limit, int windowSeconds) {
    }
}
