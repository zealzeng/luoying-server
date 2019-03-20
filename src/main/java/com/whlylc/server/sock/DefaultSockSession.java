package com.whlylc.server.sock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultSockSession /*extends ConcurrentSession*/ implements SockSession {

    private ChannelHandlerContext ctx = null;

    public DefaultSockSession(ChannelHandlerContext channelHandlerContext) {
        this.ctx = channelHandlerContext;
    }

    @Override
    public void invalidate() {
        this.ctx.channel().close();
    }

    @Override
    public void write(byte[] bytes) {
        //ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        this.ctx.writeAndFlush(byteBuf);

    }

    @Override
    public void write(CharSequence cs) {
        write(cs, CharsetUtil.UTF_8);
    }

    @Override
    public void write(CharSequence cs, Charset charset) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, charset);
        this.ctx.writeAndFlush(byteBuf);
    }


    @Override
    public void setAttribute(String name, Object o) {
        AttributeKey<Object> key = AttributeKey.valueOf(name);
        Attribute<Object> attr = this.ctx.channel().attr(key);
        attr.set(o);
    }

    @Override
    public Object getAttribute(String name) {
         AttributeKey<Object> key = AttributeKey.valueOf(name);
        Attribute<Object> attr = this.ctx.channel().attr(key);
        return attr.get();
    }
}
