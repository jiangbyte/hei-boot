package github.jiangbyte.io.common.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis 模块配置属性：连接、序列化与业务键前缀等。
 *
 * Author: Charlie
 */
@ConfigurationProperties(prefix = "hei.redis")
public class HeiRedisProperties {

    /**
     * 应用层辅助工具的逻辑 key 前缀（{@link github.jiangbyte.io.common.redis.RedisKeys}）。
     */
    private String keyPrefix = "hei";

    private int threads = 16;

    private int nettyThreads = 32;

    /** Redis 命令超时（毫秒）。 */
    private int timeout = 3000;

    /** TCP 连接超时（毫秒）。 */
    private int connectTimeout = 10000;

    /** 空闲连接回收超时（毫秒）。 */
    private int idleConnectionTimeout = 10000;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize = 24;

    private int subscriptionConnectionPoolSize = 50;

    private int subscriptionConnectionMinimumIdleSize = 1;

    /** 保活 Ping 间隔（毫秒）；0 表示关闭。 */
    private int pingConnectionInterval = 30_000;

    private boolean keepAlive = true;

    private boolean tcpNoDelay = true;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public int getPingConnectionInterval() {
        return pingConnectionInterval;
    }

    public void setPingConnectionInterval(int pingConnectionInterval) {
        this.pingConnectionInterval = pingConnectionInterval;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }
}
