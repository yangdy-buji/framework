/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package leap.lang.http.client.apache;

import leap.lang.http.HTTP;
import leap.lang.http.QueryStringBuilder;
import leap.lang.http.client.HttpRequest;
import leap.lang.http.client.HttpResponse;
import leap.lang.http.client.HttpResponseHandler;
import leap.lang.http.exception.HttpException;
import leap.lang.logging.Log;
import leap.lang.logging.LogFactory;
import leap.lang.net.Urls;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderGroup;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ApacheHttpRequest implements HttpRequest {

    private static final Log log = LogFactory.get(ApacheHttpRequest.class);

    private final HttpClient client;
    private final Charset    charset;
    private final String     uri;

    private final HeaderGroup         headers     = new HeaderGroup();
    private final QueryStringBuilder  queryString = new QueryStringBuilder();
    private final List<NameValuePair> formParams  = new ArrayList<>();

    private HTTP.Method     method;
    private byte[]          body;
    private InputStream     inputStream;
    private HttpRequestBase request;
    private HttpEntity      entity;

    ApacheHttpRequest(ApacheHttpClient client, String url) {
        this.client  = client.getHttpClient();
        this.charset = client.getDefaultCharset();
        this.uri     = url;
    }

    @Override
    public HttpRequest setCookie(String name, String value) {
        return this;
    }

    @Override
    public HttpRequest setHeader(String name, String value) {
        headers.updateHeader(new BasicHeader(name, value));
        return this;
    }

    @Override
    public HttpRequest addHeader(String name, String value) {
        headers.addHeader(new BasicHeader(name, value));
        return this;
    }

    @Override
    public HttpRequest setBody(byte[] data) {
        this.body = data;
        return this;
    }

    @Override
    public HttpRequest setBody(InputStream is) {
        this.inputStream = is;
        return this;
    }

    @Override
    public HttpRequest addQueryParam(String name, String value) {
        queryString.add(name, value);
        return this;
    }

    @Override
    public HttpRequest addFormParam(String name, String value) {
        formParams.add(new BasicNameValuePair(name, value));
        return this;
    }

    @Override
    public HttpRequest setMethod(HTTP.Method method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpResponse send() {
        String url = buildRequestUrl();
        try {
            newRequest(url);

            log.debug("Sending '{}' request to '{}'...", method, url);

            ApacheHttpResponse response = new ApacheHttpResponse(client.execute(request) );

            if(log.isDebugEnabled()) {
                log.debug("Response result : [status={}, content-type='{}', content-length={}]",
                        response.getStatus(),
                        response.getContentType(),
                        response.getContentLength());
            }
            return response;
        } catch (Exception e) {
            throw new HttpException("Error send http request : " + e.getMessage(),e);
        }finally{
            if(null != request) {
                request.releaseConnection();
            }
        }
    }

    @Override
    public HttpRequest send(HttpResponseHandler handler) {

        String url = buildRequestUrl();
        try {
            newRequest(url);

            log.debug("Sending '{}' request to '{}'...", method, url);

            client.execute(request, r -> {

                ApacheHttpResponse response = new ApacheHttpResponse(r);

                if(log.isDebugEnabled()) {
                    log.debug("Handling response : [status={}, content-type='{}', content-length={}]",
                            response.getStatus(),
                            response.getContentType(),
                            response.getContentLength());
                }

                handler.handleResponse(response);

                return null;
            });

        } catch (Exception e) {
            throw new HttpException("Error send http request : " + e.getMessage(),e);
        }finally{
            if(null != request) {
                request.releaseConnection();
            }
        }

        return this;
    }

    protected String buildRequestUrl(){
        String url = uri;

        if(!queryString.isEmpty()) {
            url = Urls.appendQueryString(url, queryString.build());
        }

        return url;
    }

    protected void initRequest() {
        if(null != body) {
            entity = new ByteArrayEntity(body);
        }else if(null != inputStream) {
            entity = new InputStreamEntity(inputStream);
        }else if(!formParams.isEmpty()) {
            entity = new UrlEncodedFormEntity(formParams, charset);
        }

        if(null != entity && method == null) {
            method = HTTP.Method.POST;
        }
    }

    protected HttpRequestBase newRequest(String url) {
        initRequest();

        if (method.equals(HTTP.Method.GET)) {
            request = new HttpGet(url);
        }

        if (method.equals(HTTP.Method.POST)) {
            request = new HttpPost(url);
        }

        if (method.equals(HTTP.Method.PUT)) {
            request = new HttpPut(url);
        }

        if (method.equals(HTTP.Method.DELETE)) {
            request = new HttpDelete(url);
        }

        if (method.equals(HTTP.Method.PATCH)) {
            request = new HttpPatch(url);
        }

        if (method.equals(HTTP.Method.HEAD)) {
            request = new HttpHead(url);
        }

        if (method.equals(HTTP.Method.OPTIONS)) {
            request = new HttpOptions(url);
        }

        if (null == request) {
            throw new IllegalStateException("Http method '" + method.name() + "' not supported now");
        }

        //set headers
        Header[] headerArray = headers.getAllHeaders();
        if(null != headerArray && headerArray.length > 0) {
            request.setHeaders(headerArray);
        }

        if(null != entity) {
            entityEnclosingRequest().setEntity(entity);
        }

        return request;
    }

    protected HttpEntityEnclosingRequest entityEnclosingRequest()  {
        if(!(request instanceof HttpEntityEnclosingRequest)){
            throw new IllegalStateException("Http method '" + method + "' does not supports request body");
        }
        return (HttpEntityEnclosingRequest)request;
    }
}