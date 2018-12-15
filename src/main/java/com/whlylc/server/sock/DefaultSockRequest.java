package com.whlylc.server.sock;

import com.whlylc.server.ServiceContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockRequest implements SockRequest {

    private static final AttributeKey<SockSession> SESSION_KEY = AttributeKey.valueOf("sock_session_key");

    private ServiceContext serviceContext = null;

    private ChannelHandlerContext ctx = null;

    private ByteBuf byteBuf = null;

    public DefaultSockRequest(ServiceContext serviceContext, ChannelHandlerContext ctx, ByteBuf byteBuf) {
        this.serviceContext = serviceContext;
        this.ctx = ctx;
        this.byteBuf = byteBuf;
    }


    @Override
    public ByteBuf getRequestBody() {
        return this.byteBuf;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    public SockSession getSession(boolean create) {
        Attribute<SockSession> attr = this.ctx.channel().attr(SESSION_KEY);
        SockSession session = attr.get();
        if (session == null && create) {
            session = new DefaultSockSession(this.ctx);
            attr.setIfAbsent(session);
        }
        return session;
    }

    @Override
    public ServiceContext getServiceContext() {
        return null;
    }
}
