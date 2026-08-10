package github.jiangbyte.io.common.log.config;

/**
 * Author: Charlie
 **/

import github.jiangbyte.io.common.log.format.HeiKeyValueStructuredLogFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeiLoggingEnvironmentPostProcessorTest {

    @Test
    void defaultsToLogstashJson() {
        MockEnvironment env = new MockEnvironment();
        new HeiLoggingEnvironmentPostProcessor().postProcessEnvironment(env, new SpringApplication());
        assertEquals("logstash", env.getProperty("logging.structured.format.console"));
        assertEquals("logstash", env.getProperty("logging.structured.format.file"));
        assertTrue(HeiLoggingEnvironmentPostProcessor.resolveJson(env));
    }

    @Test
    void keyValueWhenJsonDisabled() {
        MockEnvironment env = new MockEnvironment();
        env.setProperty("hei.logging.json", "false");
        new HeiLoggingEnvironmentPostProcessor().postProcessEnvironment(env, new SpringApplication());
        assertEquals(
                HeiKeyValueStructuredLogFormatter.class.getName(),
                env.getProperty("logging.structured.format.console"));
    }
}
