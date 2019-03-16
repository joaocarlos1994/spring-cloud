package com.thoughtmechanix.zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thoughtmechanix.zuulsvr.model.AbTestingRoute;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class SpecialRoutesFilter extends ZuulFilter {

    private static final int FILTER_ORDER =  1;
    private static final boolean SHOULD_FILTER = false;

    private final FilterUtils filterUtils;
    private final RestTemplate restTemplate;
    private final ProxyRequestHelper helper = new ProxyRequestHelper();

    @Autowired
    public SpecialRoutesFilter(final FilterUtils filterUtils, final RestTemplate restTemplate) {
        this.filterUtils = filterUtils;
        this.restTemplate = restTemplate;
    }

    @Override
    public String filterType() {
        return filterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();

        //Executes call to SpecialRoutes service to determine if there is a routing record for this org
        AbTestingRoute abTestRoute =  getAbRoutingInfo(filterUtils.getServiceId());

        //The useSpecialRoute() method will take the weight of the route, generate a random number,
        //and determine if you’re going and determine if you’re going the alternative service.
        if (abTestRoute != null && useSpecialRoute(abTestRoute)) {
            //If there’s a routing record, build the full URL (with path) to the service location
            //specified by the specialroutes service.
            final String route = buildRouteString(ctx.getRequest().getRequestURI(),
                                                  abTestRoute.getEndpoint(),
                                                  ctx.get("serviceId").toString());
            //The forwardToSpecialRoute() method does the work of
            //forwarding onto the alternative service.
            forwardToSpecialRoute(route);
        }
        return null;
    }

    public boolean useSpecialRoute(final AbTestingRoute testRoute) {
        final Random random = new Random();

        if (testRoute.getActive().equals("N")) {
            return false;
        }

        int value = random.nextInt((10 - 1) + 1) + 1;

        if (testRoute.getWeight() < value) {
            return true;
        }
        return false;
    }

    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName){
        int index = oldEndpoint.indexOf(serviceName);

        String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        System.out.println("Target route: " + String.format("%s/%s", newEndpoint, strippedRoute));
        return String.format("%s/%s", newEndpoint, strippedRoute);
    }

    private void forwardToSpecialRoute(final String route) {
        final RequestContext context = RequestContext.getCurrentContext();
        final HttpServletRequest request = context.getRequest();
        final MultiValueMap<String, String> headers = helper.buildZuulRequestHeaders(request);
        final MultiValueMap<String, String> params = helper.buildZuulRequestQueryParams(request);
        final String verb = getVerb(request);
        final InputStream requestEntity = getRequestBody(request);

        if (request.getContentLength() < 0) {
            context.setChunkedRequestBody();
        }

        this.helper.addIgnoredHeaders();
        final HttpResponse response;

        try(final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            response = forward(
                    httpClient,
                    verb,
                    route,
                    request,
                    headers,
                    params,
                    requestEntity);
            setResponse(response);
        } catch (Exception e) {}

    }

    private AbTestingRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestingRoute> restExchange = null;
        try {
            restExchange = restTemplate.exchange(
                    "http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET, null, AbTestingRoute.class, serviceName);
        } catch (final HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw ex;
            }
        }
        return restExchange.getBody();
    }

    private String getVerb(HttpServletRequest request) {
        String sMethod = request.getMethod();
        return sMethod.toUpperCase();
    }

    private InputStream getRequestBody(HttpServletRequest request) {
        try(InputStream requestEntity = request.getInputStream())  {
            return requestEntity;
        } catch (final IOException ex) {
            //No request body is ok
            return null;
        }
    }

    private HttpResponse forward(HttpClient httpclient, String verb, String uri,
                                 HttpServletRequest request, MultiValueMap<String, String> headers,
                                 MultiValueMap<String, String> params, InputStream requestEntity)
            throws Exception {
        Map<String, Object> info = this.helper.debug(verb, uri, headers, params,
                requestEntity);
        URL host = new URL( uri );
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        int contentLength = request.getContentLength();
        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
                request.getContentType() != null
                        ? ContentType.create(request.getContentType()) : null);
        switch (verb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(uri);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(uri);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(uri );
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, uri);

        }
        try {
            httpRequest.setHeaders(convertHeaders(headers));
            HttpResponse zuulResponse = forwardRequest(httpclient, httpHost, httpRequest);

            return zuulResponse;
        }
        finally {
        }
    }

    private HttpHost getHttpHost(URL host) {
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(),
                host.getProtocol());
        return httpHost;
    }

    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        final List<Header> list = new LinkedList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        return list.toArray(new BasicHeader[0]);
    }

    private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost,
                                        HttpRequest httpRequest) throws IOException {
        return httpclient.execute(httpHost, httpRequest);
    }

    private void setResponse(HttpResponse response) throws IOException {
        this.helper.setResponse(response.getStatusLine().getStatusCode(),
                response.getEntity() == null ? null : response.getEntity().getContent(),
                revertHeaders(response.getAllHeaders()));
    }

    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Stream.of(headers).forEach(header -> {
            final String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new LinkedList<>());
            }
            map.get(name).add(header.getValue());
        });
        return map;
    }

}
