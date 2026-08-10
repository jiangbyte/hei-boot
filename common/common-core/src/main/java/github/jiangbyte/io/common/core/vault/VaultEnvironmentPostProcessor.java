package github.jiangbyte.io.common.core.vault;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 可选的 HashiCorp Vault KV v2 启动引导。通过 {@code hei.vault.enabled=true} 开启。
 * 支持 token 或 AppRole 登录；将 {@code data.data} 展平注入 Spring Environment。
 *
 * Author: Charlie
 */
public class VaultEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String PROPERTY_SOURCE = "heiVaultKv";

    /** 在应用启动早期从 Vault 拉取密钥并注入 Environment。 */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!environment.getProperty("hei.vault.enabled", Boolean.class, false)) {
            return;
        }
        String uri = trimSlash(environment.getProperty("hei.vault.uri", ""));
        if (!StringUtils.hasText(uri)) {
            throw new IllegalStateException("hei.vault.enabled=true but hei.vault.uri is empty");
        }
        String mount = environment.getProperty("hei.vault.kv-mount", "secret");
        String path = environment.getProperty("hei.vault.kv-path", "hei/boot");
        String namespace = environment.getProperty("hei.vault.namespace", "");
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
            String token = resolveToken(environment, client, uri, namespace);
            Map<String, Object> secrets = fetchKv(client, uri, mount, path, token, namespace);
            if (!secrets.isEmpty()) {
                environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE, secrets));
            }
        } catch (IllegalStateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to load secrets from Vault: " + ex.getMessage(), ex);
        }
    }

    private static String resolveToken(ConfigurableEnvironment env, HttpClient client, String uri, String namespace)
            throws Exception {
        String token = env.getProperty("hei.vault.token", "");
        if (StringUtils.hasText(token)) {
            return token;
        }
        String roleId = env.getProperty("hei.vault.role-id", "");
        String secretId = env.getProperty("hei.vault.secret-id", "");
        if (!StringUtils.hasText(roleId) || !StringUtils.hasText(secretId)) {
            throw new IllegalStateException("Vault requires hei.vault.token or role-id + secret-id");
        }
        String body = MAPPER.createObjectNode()
                .put("role_id", roleId)
                .put("secret_id", secretId)
                .toString();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/v1/auth/approle/login"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body));
        applyNamespace(builder, namespace);
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new IllegalStateException("Vault AppRole login failed: HTTP " + response.statusCode());
        }
        JsonNode clientToken = MAPPER.readTree(response.body()).path("auth").path("client_token");
        if (!clientToken.isTextual() || !StringUtils.hasText(clientToken.asText())) {
            throw new IllegalStateException("Vault AppRole login returned no client_token");
        }
        return clientToken.asText();
    }

    private static Map<String, Object> fetchKv(HttpClient client, String uri, String mount, String path,
                                               String token, String namespace) throws Exception {
        String secretPath = trimSlash(mount) + "/data/" + trimLeadingSlash(path);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/v1/" + secretPath))
                .timeout(Duration.ofSeconds(10))
                .header("X-Vault-Token", token)
                .GET();
        applyNamespace(builder, namespace);
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new IllegalStateException("Vault KV read failed: HTTP " + response.statusCode()
                    + " path=" + secretPath);
        }
        JsonNode data = MAPPER.readTree(response.body()).path("data").path("data");
        Map<String, Object> secrets = new LinkedHashMap<>();
        if (data != null && data.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = data.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                JsonNode value = entry.getValue();
                if (value == null || value.isNull()) {
                    continue;
                }
                secrets.put(entry.getKey(), value.isValueNode() ? value.asText() : value.toString());
            }
        }
        return secrets;
    }

    private static void applyNamespace(HttpRequest.Builder builder, String namespace) {
        if (StringUtils.hasText(namespace)) {
            builder.header("X-Vault-Namespace", namespace);
        }
    }

    private static String trimSlash(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        while (trimmed.endsWith("/")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }

    private static String trimLeadingSlash(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        while (trimmed.startsWith("/")) {
            trimmed = trimmed.substring(1);
        }
        return trimmed;
    }

    /** 处理器优先级：尽早注入 Vault 属性源。 */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 20;
    }
}
