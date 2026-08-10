package github.jiangbyte.io.common.oss.s3;

import github.jiangbyte.io.common.core.exception.BizException;
import github.jiangbyte.io.common.oss.StorageService;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.time.Duration;
import java.util.function.Supplier;

/**
 * 带重试/容错包装的 S3 存储服务装饰器。
 *
 * Author: Charlie
 */
public class ResilientS3StorageService implements StorageService {

    private final StorageService delegate;
    private final CircuitBreaker circuitBreaker;
    private final Bulkhead bulkhead;

    public ResilientS3StorageService(StorageService delegate, CircuitBreaker circuitBreaker, Bulkhead bulkhead) {
        this.delegate = delegate;
        this.circuitBreaker = circuitBreaker;
        this.bulkhead = bulkhead;
    }

    /** 带容错地上传对象。 */
    @Override
    public String put(String objectKey, InputStream inputStream, long contentLength, String contentType) {
        return decorate(() -> delegate.put(objectKey, inputStream, contentLength, contentType));
    }

    /** 带容错地删除对象。 */
    @Override
    public void delete(String objectKey) {
        decorate(() -> {
            delegate.delete(objectKey);
            return null;
        });
    }

    /** 带容错地加载对象。 */
    @Override
    public Resource load(String objectKey) {
        return decorate(() -> delegate.load(objectKey));
    }

    /** 返回对象公开 URL。 */
    @Override
    public String publicUrl(String objectKey) {
        return delegate.publicUrl(objectKey);
    }

    /** 带容错地生成预签名 URL。 */
    @Override
    public String presignedUrl(String objectKey, Duration ttl) {
        return decorate(() -> delegate.presignedUrl(objectKey, ttl));
    }

    private <T> T decorate(Supplier<T> supplier) {
        Supplier<T> decorated = supplier;
        if (bulkhead != null) {
            decorated = Bulkhead.decorateSupplier(bulkhead, decorated);
        }
        if (circuitBreaker != null) {
            decorated = CircuitBreaker.decorateSupplier(circuitBreaker, decorated);
        }
        try {
            return decorated.get();
        } catch (CallNotPermittedException | BulkheadFullException ex) {
            throw new BizException(503, "Object storage temporarily unavailable");
        }
    }
}
