package com.whlylc.server.sock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockResponse  implements SockResponse {

    private ChannelHandlerContext ctx = null;

    public DefaultSockResponse(ChannelHandlerContext ctx) {
        this.ctx = ctx;
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
