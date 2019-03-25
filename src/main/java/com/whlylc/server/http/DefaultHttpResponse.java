package com.whlylc.server.http;

import com.whlylc.server.ConnectionFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Zeal on 2018/9/16 0016.
 */
public class DefaultHttpResponse implements HttpResponse {

    private FullHttpResponse response = null;

    private ChannelHandlerContext ctx  = null;

    private Charset characterEncoding = StandardCharsets.UTF_8;

    private HttpConnection connection = null;

    public DefaultHttpResponse(ChannelHandlerContext ctx, HttpConnection connection) {
        this.ctx = ctx;
        this.connection = connection;
        //FIXME Support dynamic http version from request
        this.response = new DefaultFullHttpResponse(HTTP_1_1, OK);
    }

    public DefaultHttpResponse(ChannelHandlerContext ctx, HttpConnection connection, FullHttpResponse response) {
        this.ctx = ctx;
        this.connection = connection;
        this.response = response;
    }

    public DefaultHttpResponse(ChannelHandlerContext ctx, HttpConnection connection, int responseCode) {
        this.ctx = ctx;
        this.connection = connection;
        HttpResponseStatus status = HttpResponseStatus.valueOf(responseCode);
        this.response = new DefaultFullHttpResponse(HTTP_1_1, status);
    }

    public void setContentType(String type) {
        this.response.headers().set(HttpHeaderNames.CONTENT_TYPE, type);
    }

    public String getContentType() {
        return this.response.headers().get(HttpHeaderNames.CONTENT_TYPE);
    }

    public void setContentLength(int len) {
        this.response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, len);
    }

    public void setHeader(String name, String value) {
        this.response.headers().set(name, value);
    }

    public String getHeader(String name) {
        return this.response.headers().get(name);
    }

    public List<String> getHeaders(String name) {
        return this.response.headers().getAll(name);
    }

    public void addHeader(String name, String value) {
        this.response.headers().add(name, value);
    }

    public void setStatus(int sc) {
        this.response.setStatus(HttpResponseStatus.valueOf(sc));
    }

    public int getStatus() {
        return this.response.status().code();
    }

    public void sendRedirect(String location) {
        this.response.headers().set(HttpHeaderNames.LOCATION, location);
    }

    public ConnectionFuture<HttpConnection> write(byte[] bytes) {
        this.response.content().writeBytes(bytes);
        return null;
    }

    public ConnectionFuture<HttpConnection> write(CharSequence cs) {
        this.response.content().writeCharSequence(cs, this.characterEncoding);
        return null;
    }

    public ConnectionFuture<HttpConnection> write(CharSequence cs, Charset charset) {
        this.response.content().writeCharSequence(cs, charset);
        return null;
    }

    public void setCharacterEncoding(Charset charset) {
        this.characterEncoding = charset;
    }

    public Charset getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public HttpConnection getConnection() {
        return this.connection;
    }


}
