package com.whlylc.server.sock;

import com.whlylc.server.ServiceContext;
import com.whlylc.server.ServiceSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2019/3/24 0024.
 */
public class DefaultSockConnection implements SockConnection {

    private ServiceContext serviceContext = null;

    private ChannelHandlerContext ctx = null;

    private SockSession session = null;

    public DefaultSockConnection(ServiceContext serviceContext, ChannelHandlerContext ctx) {
        this.serviceContext = serviceContext;
        this.ctx = ctx;
    }

    @Override
    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return ctx;
    }

    @Override
    public void close() {
        //Close whole pipeline
        this.ctx.channel().close();
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
    public void write(byte[] bytes) {
        write(bytes, SockResponse.DO_NOTHING);
    }

    @Override
    public void write(CharSequence cs) {
        write(cs, CharsetUtil.UTF_8);
    }

    @Override
    public void write(CharSequence cs, Charset charset) {
        write(cs, charset, SockResponse.DO_NOTHING);
    }

    @Override
    public void write(byte[] bytes, int futureAction) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
        writeFutureListener(future, futureAction);
    }

    @Override
    public void write(CharSequence cs, int futureAction) {
        write(cs, CharsetUtil.UTF_8, futureAction);
    }

    @Override
    public void write(CharSequence cs, Charset charset, int futureAction) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
        writeFutureListener(future, futureAction);
    }

    protected void writeFutureListener(ChannelFuture future, int futureAction) {
        if (futureAction == SockResponse.CLOSE) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
        else if (futureAction == SockResponse.CLOSE_ON_FAILURE) {
            future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
        else if (futureAction == SockResponse.FIRE_EXCEPTION_ON_FAILURE) {
            future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }
}
