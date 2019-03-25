package com.whlylc.server.sock;

import com.whlylc.server.ConnectionFuture;
import com.whlylc.server.ServiceContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2019/3/24 0024.
 */
public class DefaultSockConnection implements SockConnection {

    private ServiceContext serviceContext = null;

    private Channel channel = null;

    private SockSession session = null;

    public DefaultSockConnection(ServiceContext serviceContext, ChannelHandlerContext ctx) {
        this.serviceContext = serviceContext;
        //this.ctx = ctx;
        this.channel = ctx.channel();
    }

    @Override
    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    @Override
    public void close() {
        this.channel.close();
    }

    @Override
    public SockSession getSession() {
        return this.session;
    }

    @Override
    public void setSession(SockSession session) {
        this.session = session;

    }


    @Override
    public ConnectionFuture<SockConnection> write(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        ChannelFuture future = this.channel.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(this, future);

    }

    @Override
    public ConnectionFuture<SockConnection> write(CharSequence cs) {
        return write(cs, CharsetUtil.UTF_8);
    }

    @Override
    public ConnectionFuture<SockConnection> write(CharSequence cs, Charset charset) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        ChannelFuture future = this.channel.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(this, future);
    }

}
