package github.jiangbyte.io.common.security.web;

/** Author: Charlie **/

import github.jiangbyte.io.common.security.config.HeiSecurityProperties;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class SecurityHeadersFilterTest {

    @Test
    void writesBaselineHeadersWithoutHstsByDefault() throws Exception {
        HeiSecurityProperties properties = new HeiSecurityProperties();
        SecurityHeadersFilter filter = new SecurityHeadersFilter(properties);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertEquals("nosniff", response.getHeader("X-Content-Type-Options"));
        assertEquals("DENY", response.getHeader("X-Frame-Options"));
        assertNull(response.getHeader("Strict-Transport-Security"));
    }

    @Test
    void writesHstsWhenConfigured() throws Exception {
        HeiSecurityProperties properties = new HeiSecurityProperties();
        properties.setHstsMaxAgeSeconds(31536000);
        properties.setHstsIncludeSubDomains(true);
        properties.setHstsPreload(true);
        SecurityHeadersFilter filter = new SecurityHeadersFilter(properties);
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(new MockHttpServletRequest(), response, mock(FilterChain.class));

        assertEquals(
                "max-age=31536000; includeSubDomains; preload",
                response.getHeader("Strict-Transport-Security"));
    }
}
