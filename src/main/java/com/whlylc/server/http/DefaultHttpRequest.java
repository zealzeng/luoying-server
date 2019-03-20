package com.whlylc.server.http;

import com.whlylc.server.ServiceContext;
import com.whlylc.server.utils.StringUtils;
import com.whlylc.server.utils.URLUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Default implementation of http request
 * Created by Zeal on 2018/9/16 0016.
 */
public class DefaultHttpRequest implements HttpRequest {

    /** Like servlet context */
    private ServiceContext serviceContext = null;

    private ChannelHandlerContext ctx = null;

    /** Default character encoding */
    private Charset characterEncoding = StandardCharsets.UTF_8;

    /** Netty http request */
    private FullHttpRequest request = null;

    /** Requst URI */
    private String uri = null;

    /** Request path */
    private String path = null;

    /** Query string */
    private String queryString = null;

    /** Request body */
    private String requestBody = null;

    /** Request parameters */
    private Map<String,List<String>> parameters = null;

    /** Attribute map */
    private Map<String,Object> attributeMap = null;

    private String scheme = null;

    private String host = null;

    private int port = 0;

    public DefaultHttpRequest(ServiceContext serviceContext, FullHttpRequest request, ChannelHandlerContext ctx) {
        this.serviceContext = serviceContext;
        this.request = request;
        this.ctx = ctx;
        parseURI();
        parseHost();
        parseBody();
    }

    private void parseHost() {
        String hostString = this.request.headers().get(HttpHeaderNames.HOST);
        //FIXME optimize it
        SslHandler ssl = ctx.channel().pipeline().get(SslHandler.class);
        if (ssl != null) {
            this.scheme = "https";
        }
        this.scheme = ssl != null ? "https" : "http";

        if (StringUtils.isNotBlank(hostString)) {
            int index = hostString.indexOf(':');
            if (index != -1) {
                this.host = hostString.substring(0, index);
                this.port = Integer.parseInt(hostString.substring(index + 1));
            }
            //FIXME We have to confirm it's http or https
            else {
                this.port = ssl != null ? 443 : 80;
            }
        }
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

    /**
     * Get http header by name
     * @param name
     * @return
     */
    public String getHeader(String name) {
        return this.request.headers().get(name);
    }

    /**
     * Get all header names
     * @return
     */
    public Set<String> getHeaderNames() {
        //FIXME Support readonly
        return this.request.headers().names();
    }

    /**
     * Get headers by name
     * @param name
     * @return
     */
    public List<String> getHeaders(String name) {
        //FIXME Support readonly
        return this.request.headers().getAll(name);
    }

    /**
     * Get integer header
     * @param name
     * @return
     */
    public int getIntHeader(String name) {
        return this.request.headers().getInt(name);
    }

    /**
     * Get http method
     * @return
     */
    public String getMethod() {
        return this.request.method().name();
    }

    /**
     * Get request URI
     * @return
     */
    public String getRequestURI() {
        return this.uri;
    }

    /**
     * Get request path
     * @return
     */
    public String getRequestPath() {
        return this.path;
    }

    /**
     * Get query string
     * @return
     */
    public String getQueryString() {
        return this.queryString;
    }

    /**
     * Get request body
     * @return
     */
    public String getRequestBody() {
        return this.requestBody;
    }

    /**
     * Get content type
     * @return
     */
    public String getContentType() {
        return this.request.headers().get(HttpHeaderNames.CONTENT_TYPE);
    }

    /**
     * Get content length
     * @return
     */
    public int getContentLength() {
        return this.request.headers().getInt(HttpHeaderNames.CONTENT_LENGTH, 0);
    }

    /**
     * Get protocol
     * @return
     */
    public String getProtocol() {
        return this.request.protocolVersion().text();
    }

    /**
     * Get scheme
     * @return
     */
    public String getScheme() {
        return this.scheme;
    }

    /**
     * Get request parameter
     * @param name
     * @return
     */
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

    /**
     * Get parameter names
     * @return
     */
    public Set<String> getParameterNames() {
        if (this.parameters == null || this.parameters.size() <= 0) {
            return null;
        }
        else {
            return this.parameters.keySet();
        }
    }

    /**
     * Get request parameter map
     * @return
     */
    public Map<String, List<String>> getParameterMap() {
        return this.parameters;
    }

    /**
     * Get server name
     * @return
     */
    public String getServerName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get server port
     * @return
     */
    public int getServerPort() {
        throw new UnsupportedOperationException();
    }


    public String getRemoteAddr() {
        throw new UnsupportedOperationException();
    }

    /**
     * Set request attribute, not thread safe
     * @param name
     * @param o
     */
    public void setAttribute(String name, Object o) {
        if (this.attributeMap == null) {
            this.attributeMap = new HashMap<>(6);
        }
        this.attributeMap.put(name, o);
    }

    /**
     * Get attribute by name
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        if (this.attributeMap == null || this.attributeMap.size() <= 0) {
            return null;
        }
        else {
            return this.attributeMap.get(name);
        }
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
        return this.serviceContext;
    }


}
