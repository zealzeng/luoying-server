package com.whlylc.server.sock;

import com.whlylc.server.ChannelServiceInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * Default implementation of socket server handler
 * Created by Zeal on 2018/10/21 0021.
 */
@ChannelHandler.Sharable
public class DefaultSockServerHandler extends ChannelServiceInboundHandler<SockService,SockConnection,SockRequest,SockResponse,ByteBuf> {


    public DefaultSockServerHandler(SockService sockApplication) {
        this.service = sockApplication;
    }

    @Override
    protected SockConnection createConnection(ChannelHandlerContext ctx) {
        return new DefaultSockConnection(service.getServiceContext(), ctx);
    }

    @Override
    protected SockRequest createRequest(ChannelHandlerContext ctx, SockConnection connection, ByteBuf msg) {
        return new DefaultSockRequest(ctx, connection, msg);
    }

    @Override
    protected SockResponse createResponse(ChannelHandlerContext ctx, SockConnection connection, ByteBuf msg) {
        return new DefaultSockResponse(ctx, connection);
    }

}
