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
public interface HttpResponse extends ServiceResponse/*<HttpConnection>*/ {

     void setContentType(String type);

     String getContentType();

     void setContentLength(int len);

     void setHeader(String name, String value);

     String getHeader(String name);

     List<String> getHeaders(String name);

     void addHeader(String name, String value);

     void setStatus(int sc);

     int getStatus();

     void sendRedirect(String location);

     void setCharacterEncoding(Charset charset);

     Charset getCharacterEncoding();

     HttpConnection getConnection();

         /**
     * Write bytes
     * @param bytes
     */
    ConnectionFuture<HttpConnection> write(byte[] bytes);

    /**
     * Default charset is UTF-8
     * @param cs
     */
    ConnectionFuture<HttpConnection> write(CharSequence cs);

    /**
     * @param cs
     */
    ConnectionFuture<HttpConnection> write(CharSequence cs, Charset charset);



}
