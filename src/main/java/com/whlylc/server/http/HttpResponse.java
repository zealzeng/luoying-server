package com.whlylc.server.http;

import com.whlylc.server.ServiceResponse;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Similar to HttpServletResponse
 * Created by Zeal on 2018/9/16 0016.
 */
public interface HttpResponse extends ServiceResponse {

    public void setContentType(String type);

    public String getContentType();

    public void setContentLength(int len);

    public void setHeader(String name, String value);

    public String getHeader(String name);

    public List<String> getHeaders(String name);

    public void addHeader(String name, String value);

    public void setStatus(int sc);

    public int getStatus();

    public void sendRedirect(String location);

//    public void write(byte[] bytes);
//
//    public void write(CharSequence cs);

    public void setCharacterEncoding(Charset charset);

    public Charset getCharacterEncoding();



}
