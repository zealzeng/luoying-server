package com.whlylc.server.sock;

import com.whlylc.server.ChannelServiceHandler;
import com.whlylc.server.ChannelServiceInboundHandler;
import com.whlylc.server.ServiceRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockServerHandler extends ChannelServiceInboundHandler<ByteBuf,SockService> { // SimpleChannelInboundHandler<ByteBuf> implements ChannelServiceHandler<SockService> {

    private SockService sockService = null;

    public DefaultSockServerHandler(SockService sockApplication) {
        this.sockService = sockApplication;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        DefaultSockRequest request = new DefaultSockRequest(sockService.getServiceContext(), ctx, byteBuf);
        //DefaultSockResponse response = new DefaultSockResponse(ctx);
        SockSession session = request.getSession(true);
        this.sockService.service((ServiceRequest) request, session);
    }

    @Override
    public SockService getService() {
        return this.sockService;
    }

    @Override
    public void setService(SockService service) {
        this.sockService = service;
    }
}
