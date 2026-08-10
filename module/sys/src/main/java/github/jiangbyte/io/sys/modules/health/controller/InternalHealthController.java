package github.jiangbyte.io.sys.modules.health.controller;

import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * 内部健康检查 API：供探活与依赖探测。
 *
 * Author: Charlie
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InternalHealthController {

    private final DataSource dataSource;
    private final RedissonClient redissonClient;
    private final ObjectProvider<ConnectionFactory> rabbitConnectionFactory;

    /** 存活探活。 */
    @GetMapping("/v1/internal/health/live")
    public Map<String, Object> live() {
        return Map.of("status", "live");
    }

    /** 就绪检查。 */
    @GetMapping("/v1/internal/health/ready")
    public ResponseEntity<Map<String, Object>> ready() {
        Map<String, Object> checks = new LinkedHashMap<>();
        boolean databaseOk = checkDatabase(checks);
        boolean redisOk = checkRedis(checks);
        boolean rabbitOk = checkRabbit(checks);

        boolean overall = databaseOk && redisOk && rabbitOk;
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", overall ? "ready" : "not_ready");
        body.put("checks", checks);
        return ResponseEntity.status(overall ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    /** 检查数据库连通性。 */
    private boolean checkDatabase(Map<String, Object> checks) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("enabled", true);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("SELECT 1");
            item.put("ok", true);
            item.put("detail", "connection ok");
            checks.put("database", item);
            return true;
        } catch (SQLException ex) {
            item.put("ok", false);
            item.put("detail", ex.getClass().getSimpleName());
            checks.put("database", item);
            return false;
        }
    }

    /** 检查 Redis 连通性。 */
    private boolean checkRedis(Map<String, Object> checks) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("enabled", true);
        try {
            redissonClient.getBucket("hei:health:ready").isExists();
            item.put("ok", true);
            item.put("detail", "connection ok");
            checks.put("redis", item);
            return true;
        } catch (Exception ex) {
            item.put("ok", false);
            item.put("detail", ex.getClass().getSimpleName());
            checks.put("redis", item);
            return false;
        }
    }

    /** 检查 RabbitMQ 连通性。 */
    private boolean checkRabbit(Map<String, Object> checks) {
        ConnectionFactory factory = rabbitConnectionFactory.getIfAvailable();
        Map<String, Object> item = new LinkedHashMap<>();

        if (factory == null) {
            item.put("enabled", false);
            item.put("ok", true);
            item.put("detail", "rabbitmq not configured");
            checks.put("rabbitmq", item);
            return true;
        }
        item.put("enabled", true);

        try (Connection connection = factory.createConnection()) {
            boolean open = connection.isOpen();
            item.put("ok", open);
            item.put("detail", open ? "connection ok" : "connection closed");
            checks.put("rabbitmq", item);
            return open;
        } catch (Exception ex) {
            item.put("ok", false);
            item.put("detail", ex.getClass().getSimpleName());
            checks.put("rabbitmq", item);
            return false;
        }
    }
}
