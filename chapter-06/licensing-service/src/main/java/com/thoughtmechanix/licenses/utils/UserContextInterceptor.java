package com.thoughtmechanix.licenses.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * The main goal of this {@code UserContextInterceptor} is ensure that the correlation id
 * gets propagated forward through {@code {@link org.springframework.web.client.RestTemplate} that
 * was defined in {@code {@link com.thoughtmechanix.licenses.Application}}
 *
 * @see  com.thoughtmechanix.licenses.Application
 *
 * */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    //The intercept() method is invoked before the actual HTTP service call
    //occurs by the RestTemplate.
    @Override
    public ClientHttpResponse intercept(final HttpRequest httpRequest, final byte[] bytes,
                                        final ClientHttpRequestExecution clientHttpRequestExecution) {
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        return null;
    }
}
