package com.whlylc.server.http;

import com.whlylc.server.ServiceContext;
import com.whlylc.server.utils.StringUtils;
import com.whlylc.server.utils.URLUtils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zeal on 2018/9/16 0016.
 */
public class DefaultHttpRequest implements HttpRequest {

    private ServiceContext serviceContext = null;

    private Charset characterEncoding = StandardCharsets.UTF_8;

    private FullHttpRequest request = null;

    private String uri = null;

    private String path = null;

    private String queryString = null;

    private String requestBody = null;

    private Map<String,List<String>> parameters = null;

    public DefaultHttpRequest(ServiceContext serviceContext, FullHttpRequest request) {
        this.serviceContext = serviceContext;
        this.request = request;
        parseURI();
        parseBody();
    }

    /**
     * Parse URI
     */
    private void parseURI() {
        String _uri = this.request.uri();
        _uri = URLUtils.urlDecode(_uri, this.characterEncoding.name());

        int index = _uri.indexOf('?');
        if (index != -1) {
            //FIXME Need to support session style parameter like  xx.jsp;jsessionid=xxx?param=value
            String _path = _uri.substring(0, index);
            //FIXME Need to filter # like xx.jsp?param=value#abc
            String _queryString = _uri.substring(index + 1);
            Map<String,List<String>> _map = URLUtils.parseURLQueryString(_queryString);
            appendRequestParameters(_map);
            this.path = _path;
//            this.uri = uriPath;
            this.queryString = _queryString;
        }
        else {
            path = "";
        }
        this.uri = _uri;
    }

    /**
     * Parse request body
     */
    private void parseBody() {
        //this.request.headers().get
        //HttpHeaderNames.CONTENT_TYPE
        ByteBuf byteBuf = this.request.content();
        if (byteBuf == null) {
            return;
        }
        //FIXME Support dynamic charset while having time
        String _requestBody = byteBuf.toString(CharsetUtil.UTF_8);
        if (StringUtils.isNotBlank(_requestBody)) {
            Map<String,List<String>> _params = URLUtils.parseURLQueryString(_requestBody);
            this.appendRequestParameters(_params);
        }
        this.requestBody = _requestBody;
    }

    private void appendRequestParameters(Map<String,List<String>> parameters) {
        if (parameters == null || parameters.size() <= 0) {
            return;
        }
        if (this.parameters == null) {
            this.parameters = new LinkedHashMap<>(parameters.size());
        }
        this.parameters.putAll(parameters);
    }

    public String getHeader(String name) {
        return this.request.headers().get(name);
    }

    public Set<String> getHeaderNames() {
        //FIXME Support readonly
        return this.request.headers().names();
    }

    public List<String> getHeaders(String name) {
        //FIXME Support readonly
        return this.request.headers().getAll(name);
    }

    public int getIntHeader(String name) {
        return this.request.headers().getInt(name);
    }

    public String getMethod() {
        return this.request.method().name();
    }

    public String getRequestURI() {
        return this.uri;
    }

    public String getRequestPath() {
        return this.path;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public String getContentType() {
        return this.request.headers().get(HttpHeaderNames.CONTENT_TYPE);
    }

    public int getContentLength() {
        return this.request.headers().getInt(HttpHeaderNames.CONTENT_LENGTH, 0);
    }

    public String getProtocol() {
        return this.request.protocolVersion().text();
    }

    public String getScheme() {
        throw new UnsupportedOperationException();
    }

    public String getParameter(String name) {
        if (this.parameters == null || this.parameters.size() <= 0) {
            return null;
        }
        List<String> values = this.parameters.get(name);
        if (values == null || values.size() <= 0) {
            return null;
        }
        else {
            return values.get(0);
        }
    }

    public Set<String> getParameterNames() {
        if (this.parameters == null || this.parameters.size() <= 0) {
            return null;
        }
        else {
            //FIXME Support readonly
            return this.parameters.keySet();
        }
    }

    public Map<String, List<String>> getParameterMap() {
        //FIXME Support readonly
        return this.parameters;
    }


    public String getServerName() {
        throw new UnsupportedOperationException();
    }

    public int getServerPort() {
        throw new UnsupportedOperationException();
    }

    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    public void setAttribute(String name, Object o) {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    public Charset getCharacterEncoding() {
        return this.characterEncoding;
    }

    public void setCharacterEncoding(Charset env) {
        this.characterEncoding = env;
    }

    public HttpSession getSession(boolean create) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServiceContext getServiceContext() {
        return null;
    }


}
