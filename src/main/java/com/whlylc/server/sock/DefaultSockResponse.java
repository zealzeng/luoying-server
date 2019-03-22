package com.whlylc.server.sock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/21 0021.
 * @deprecated Use DefaultSocketSession instead
 */
public class DefaultSockResponse  implements SockResponse {

    private ChannelHandlerContext ctx = null;

    public DefaultSockResponse(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void write(byte[] bytes) {
        //FIXME copy or wrap?
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        this.ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void write(CharSequence cs) {
        //FIXME copy or wrap?
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, CharsetUtil.UTF_8);
        this.ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void write(CharSequence cs, Charset charset) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        this.ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void write(byte[] bytes, int futureAction) {

    }

    @Override
    public void write(CharSequence cs, int futureAction) {

    }

    @Override
    public void write(CharSequence cs, Charset charset, int futureAction) {

    }
}
