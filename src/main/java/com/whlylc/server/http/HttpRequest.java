package com.whlylc.server.http;

import com.whlylc.server.ServiceRequest;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Similar to HttpServletRequest
 * Created by Zeal on 2018/9/16 0016.
 */
public interface HttpRequest extends ServiceRequest {

    String getHeader(String name);

    Set<String> getHeaderNames();

    List<String> getHeaders(String name);

    int getIntHeader(String name);

    String getMethod();

    String getRequestURI();

    String getRequestPath();

    String getQueryString();

    String getRequestBody();

    String getContentType();

    String getProtocol();

    String getScheme();

    String getParameter(String name);

    Set<String> getParameterNames();

    Map<String, List<String>> getParameterMap();

    String getServerName();

    int getServerPort();

    String getRemoteAddr();

//    void setAttribute(String name, Object o);
//
//    Object getAttribute(String name);

    Charset getCharacterEncoding();

    void setCharacterEncoding(Charset env);

    HttpSession getSession(boolean create);
}
