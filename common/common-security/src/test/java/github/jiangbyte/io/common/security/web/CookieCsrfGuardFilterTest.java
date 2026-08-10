package github.jiangbyte.io.common.security.web;

/** Author: Charlie **/

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CookieCsrfGuardFilterTest {

    @Test
    void skipsWhenCookieAuthDisabled() throws Exception {
        CookieCsrfGuardFilter filter = new CookieCsrfGuardFilter(false);
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/admin/logout");
        request.setCookies(new Cookie("Authorization", "tok"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void blocksMutatingApiWithAuthCookieButNoCsrfHeader() throws Exception {
        CookieCsrfGuardFilter filter = new CookieCsrfGuardFilter(true);
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/admin/logout");
        request.setCookies(new Cookie("Authorization", "tok"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain, never()).doFilter(request, response);
        assertEquals(403, response.getStatus());
    }

    @Test
    void allowsWhenXRequestedWithPresent() throws Exception {
        CookieCsrfGuardFilter filter = new CookieCsrfGuardFilter(true);
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/admin/logout");
        request.setCookies(new Cookie("Authorization", "tok"));
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void allowsGetWithoutCsrfHeader() throws Exception {
        CookieCsrfGuardFilter filter = new CookieCsrfGuardFilter(true);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/admin/auth/me");
        request.setCookies(new Cookie("Authorization", "tok"));
        FilterChain chain = mock(FilterChain.class);

        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, chain);

        verify(chain).doFilter(eq(request), any());
    }
}
