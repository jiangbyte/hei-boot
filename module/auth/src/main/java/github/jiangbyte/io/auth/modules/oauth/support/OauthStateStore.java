package github.jiangbyte.io.auth.modules.oauth.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.jiangbyte.io.common.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.UUID;

/**
 * OAuth state 一次性存储。
 *
 * Author: Charlie
 */
@Component
@RequiredArgsConstructor
public class OauthStateStore {

    private static final Duration TTL = Duration.ofMinutes(10);

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;

    public String save(OauthStatePayload payload) {
        String state = UUID.randomUUID().toString().replace("-", "");
        try {
            redissonClient.getBucket(key(state)).set(objectMapper.writeValueAsString(payload), TTL);
        } catch (JsonProcessingException e) {
            throw new BizException("无法创建 OAuth state");
        }
        return state;
    }

    public OauthStatePayload consume(String state) {
        if (!StringUtils.hasText(state)) {
            return null;
        }
        RBucket<String> bucket = redissonClient.getBucket(key(state.trim()));
        String json = bucket.getAndDelete();
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, OauthStatePayload.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private static String key(String state) {
        return "oauth:state:" + state;
    }
}
