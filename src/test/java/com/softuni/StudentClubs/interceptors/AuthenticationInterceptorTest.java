package com.softuni.StudentClubs.interceptors;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationInterceptorTest {

// ...

    @Test
    public void testPreHandle_AdminRole_Success() throws Exception {
        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));

        Authentication authentication = securityContext.getAuthentication();
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                AuthorityUtils.createAuthorityList("ADMIN"));

        SecurityContextHolder.setContext(securityContext);

        boolean result = interceptor.preHandle(request, response, null);

        assertTrue(result);
    }



    @Test
    public void testPreHandle_UserRole_RedirectToRoot() throws Exception {
        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));

        Authentication authentication = securityContext.getAuthentication();
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                AuthorityUtils.createAuthorityList("USER"));

        SecurityContextHolder.setContext(securityContext);

        boolean result = interceptor.preHandle(request, response, null);

        assertFalse(result);
        assertEquals("/", response.getRedirectedUrl());
    }

    @Test
    public void testPreHandle_NoAuthentication_RedirectToLogin() throws Exception {

        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        boolean result = interceptor.preHandle(request, response, null);

        assertFalse(result);
        assertEquals("/login", response.getRedirectedUrl());
    }
}
