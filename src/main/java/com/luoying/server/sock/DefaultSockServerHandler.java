package com.luoying.server.sock;

import com.luoying.server.ChannelServiceHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockServerHandler extends SimpleChannelInboundHandler<ByteBuf> implements ChannelServiceHandler {

    private SockService sockService = null;

    public DefaultSockServerHandler(SockService sockApplication) {
        this.sockService = sockApplication;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        DefaultSockRequest request = new DefaultSockRequest(sockService.getServiceContext(), ctx, byteBuf);
        DefaultSockResponse response = new DefaultSockResponse(ctx);
        this.sockService.service(request, response);
    }


}
