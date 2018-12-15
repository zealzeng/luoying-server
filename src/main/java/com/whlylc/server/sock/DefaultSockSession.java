package com.whlylc.server.sock;

import com.whlylc.server.ConcurrentSession;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultSockSession extends ConcurrentSession implements SockSession {

    private ChannelHandlerContext channelHandlerContext = null;

    public DefaultSockSession(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public void invalidate() {
        this.channelHandlerContext.channel().close();
    }



}
