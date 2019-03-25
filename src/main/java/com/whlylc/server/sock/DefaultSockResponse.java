package com.whlylc.server.sock;

import com.whlylc.server.ConnectionFuture;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockResponse implements SockResponse<SockConnection> {

    private ChannelHandlerContext ctx = null;

    private SockConnection connection = null;

    public DefaultSockResponse(ChannelHandlerContext ctx, SockConnection connection) {
        this.ctx = ctx;
        this.connection = connection;
    }

    @Override
    public ConnectionFuture<SockConnection> write(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(connection, future);
    }

    @Override
    public ConnectionFuture<SockConnection> write(CharSequence cs) {
        return write(cs, CharsetUtil.UTF_8);
    }

    @Override
    public ConnectionFuture write(CharSequence cs, Charset charset) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
        return new ConnectionFuture<>(connection, future);
    }

//    @Override
//    public void write(byte[] bytes, int futureAction) {
//        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
//        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
//        writeFutureListener(future, futureAction);
//    }

//    @Override
//    public void write(CharSequence cs, int futureAction) {
//        write(cs, CharsetUtil.UTF_8, futureAction);
//    }
//
//    @Override
//    public void write(CharSequence cs, Charset charset, int futureAction) {
//        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
//        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
////        ConnectionFuture connectionFuture = new ConnectionFuture(connection, future);
////        connectionFuture.addListener(new ConnectionFutureListener() {
////            @Override
////            public void complete(ConnectionFuture connectionFuture) {
////
////            }
////        });
//        writeFutureListener(future, futureAction);
//    }
//
//    //=========================================
////    public ConnectionFuture write(CharSequence cs, Charset charset) {
////        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
////        ChannelFuture future = this.ctx.writeAndFlush(byteBuf);
////        return new ConnectionFuture(connection, future);
////    }

    @Override
    public SockConnection getConnection() {
        return this.connection;
    }

//    protected void writeFutureListener(ChannelFuture future, int futureAction) {
//        ChannelFutureUtils.writeFutureListener(future, futureAction);
//    }
}
