package com.whlylc.server.http;

import com.whlylc.server.ConnectionFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

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

    private volatile boolean flushed = false;

    private boolean keepAlive = false;

    public DefaultHttpResponse(ChannelHandlerContext ctx, HttpConnection connection, FullHttpRequest msg) {
        this.ctx = ctx;
        this.connection = connection;
        //FIXME Support dynamic http version from request
        this.response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        keepAlive = HttpUtil.isKeepAlive(msg);
    }

    public DefaultHttpResponse(ChannelHandlerContext ctx, HttpConnection connection, int responseCode) {
        this.ctx = ctx;
        this.connection = connection;
        HttpResponseStatus status = HttpResponseStatus.valueOf(responseCode);
        this.response = new DefaultFullHttpResponse(HTTP_1_1, status);
    }

    public FullHttpResponse getNativeResponse() {
        return response;
    }

    public void setContentType(String type) {
        this.response.headers().set(HttpHeaderNames.CONTENT_TYPE, type);
    }

    public String getContentType() {
        return this.response.headers().get(HttpHeaderNames.CONTENT_TYPE);
    }

    public HttpResponse setContentLength(int len) {
        this.response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, len);
        return this;
    }

    public HttpResponse setHeader(String name, String value) {
        this.response.headers().set(name, value);
        return this;
    }

    public String getHeader(String name) {
        return this.response.headers().get(name);
    }

    public List<String> getHeaders(String name) {
        return this.response.headers().getAll(name);
    }

    public HttpResponse addHeader(String name, String value) {
        this.response.headers().add(name, value);
        return this;
    }

    public HttpResponse setStatus(int sc) {
        this.response.setStatus(HttpResponseStatus.valueOf(sc));
        return this;
    }

    public int getStatus() {
        return this.response.status().code();
    }

    public HttpResponse sendRedirect(String location) {
        this.response.headers().set(HttpHeaderNames.LOCATION, location);
        return this;
    }

    public HttpResponse write(byte[] bytes) {
        this.response.content().writeBytes(bytes);
        return this;
    }

    public HttpResponse write(CharSequence cs) {
        this.response.content().writeCharSequence(cs, this.characterEncoding);
        return this;
    }

    public HttpResponse write(CharSequence cs, Charset charset) {
        this.response.content().writeCharSequence(cs, charset);
        return this;
    }

    @Override
    public HttpResponse flush() {
        if (flushed) {
            return this;
        }
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        if (!response.headers().contains(HttpHeaderNames.CONTENT_TYPE)) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        }
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.writeAndFlush(response);
        }
        this.flushed = true;
        return this;
    }

    public HttpResponse setCharacterEncoding(Charset charset) {
        this.characterEncoding = charset;
        return this;
    }

    public Charset getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public HttpConnection getConnection() {
        return this.connection;
    }


}
