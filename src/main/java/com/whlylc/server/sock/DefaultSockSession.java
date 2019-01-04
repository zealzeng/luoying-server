package com.whlylc.server.sock;

import com.whlylc.server.ConcurrentSession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultSockSession extends ConcurrentSession implements SockSession {

    private ChannelHandlerContext channelHandlerContext = null;

    /**
     * @param channelHandlerContext
     */
    public DefaultSockSession(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    /**
     */
    @Override
    public void invalidate() {
        this.channelHandlerContext.channel().close();
    }

    /**
     * @param bytes
     */
    @Override
    public void write(byte[] bytes) {
        //FIXME copy or wrap?
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        this.channelHandlerContext.writeAndFlush(byteBuf);
    }

    /**
     * @param cs
     */
    @Override
    public void write(CharSequence cs) {
        //FIXME copy or wrap?
        ByteBuf byteBuf = Unpooled.copiedBuffer(cs, CharsetUtil.UTF_8);
        this.channelHandlerContext.writeAndFlush(byteBuf);
    }
}
