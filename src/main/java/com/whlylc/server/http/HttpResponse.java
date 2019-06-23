package com.whlylc.server.http;

import com.whlylc.server.ConnectionFuture;
import com.whlylc.server.ServiceConnection;
import com.whlylc.server.ServiceResponse;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Similar to HttpServletResponse
 * Created by Zeal on 2018/9/16 0016.
 */
public interface HttpResponse extends ServiceResponse {

     void setContentType(String type);

     String getContentType();

     HttpResponse setContentLength(int len);

     HttpResponse setHeader(String name, String value);

     String getHeader(String name);

     List<String> getHeaders(String name);

     HttpResponse addHeader(String name, String value);

     HttpResponse setStatus(int sc);

     int getStatus();

     HttpResponse sendRedirect(String location);

     HttpResponse setCharacterEncoding(Charset charset);

     Charset getCharacterEncoding();

     HttpConnection getConnection();

     /**
     * Write bytes
     * @param bytes
     */
    HttpResponse write(byte[] bytes);

    /**
     * Default charset is UTF-8
     * @param cs
     */
    HttpResponse write(CharSequence cs);

    /**
     * @param cs
     */
    HttpResponse write(CharSequence cs, Charset charset);

    /**
     */
    HttpResponse flush();

}
