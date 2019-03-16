package com.thoughtmechanix.organization.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        final String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
        final String userId = httpServletRequest.getHeader(UserContext.USER_ID);
        final String authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN);
        final String orgId = httpServletRequest.getHeader(UserContext.ORG_ID);


        UserContextHolder.getContext().setCorrelationId(correlationId);
        UserContextHolder.getContext().setUserId(userId);
        UserContextHolder.getContext().setAuthToken(authToken);
        UserContextHolder.getContext().setOrgId(orgId);

        logger.debug("Exiting the UserContextFilter");
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}