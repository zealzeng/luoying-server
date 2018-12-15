package com.whlylc.server.sock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

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
}
