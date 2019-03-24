package com.whlylc.server.http;

import com.whlylc.server.ServiceContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Zeal on 2019/3/24 0024.
 */
public class DefaultHttpConnection implements HttpConnection {

    private ServiceContext serviceContext = null;

    private ChannelHandlerContext ctx = null;

    private FullHttpResponse response = null;

    private Charset characterEncoding = StandardCharsets.UTF_8;

    public DefaultHttpConnection(ServiceContext serviceContext, ChannelHandlerContext ctx) {
        this.serviceContext = serviceContext;
        this.ctx = ctx;
        //FIXME Support dynamic http version from request
        this.response = new DefaultFullHttpResponse(HTTP_1_1, OK);
    }

    public void write(byte[] bytes) {
        this.response.content().writeBytes(bytes);
    }

    public void write(CharSequence cs) {
        this.response.content().writeCharSequence(cs, this.characterEncoding);
    }

    public void write(CharSequence cs, Charset charset) {
        this.response.content().writeCharSequence(cs, charset);
    }

    @Override
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }

    @Override
    public void close() {
        this.ctx.channel().close();
    }
}
