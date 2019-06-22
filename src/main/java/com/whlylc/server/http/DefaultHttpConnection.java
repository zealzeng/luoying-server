package com.whlylc.server.http;

import com.whlylc.server.ConnectionFuture;
import com.whlylc.server.ServiceContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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

    private Channel channel = null;

//    private DefaultFullHttpResponse response = null;

    private Charset characterEncoding = StandardCharsets.UTF_8;

    public DefaultHttpConnection(ServiceContext serviceContext, ChannelHandlerContext ctx) {
        this.serviceContext = serviceContext;
        this.channel = ctx.channel();
        //this.response = new DefaultFullHttpResponse(HTTP_1_1, OK);
    }

//    public DefaultFullHttpResponse getResponse() {
//        return response;
//    }

    public ConnectionFuture<HttpConnection> write(byte[] bytes) {
         ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        ChannelFuture future = this.channel.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(this, future);
    }

    public ConnectionFuture<HttpConnection> write(CharSequence cs) {
        return this.write(cs, characterEncoding);
    }

    public ConnectionFuture<HttpConnection> write(CharSequence cs, Charset charset) {
       ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        ChannelFuture future = this.channel.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(this, future);
    }

    @Override
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }

    @Override
    public void close() {
        this.channel.close();
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public void setSession(HttpSession session) {
    }
}
