package com.whlylc.server.sock;

import com.whlylc.server.ServiceContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of socket request
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockRequest implements SockRequest {

    /** Session key */
    private static final AttributeKey<SockSession> SESSION_KEY = AttributeKey.valueOf("sock_session_key");

    /** Like servlet context */
    private ServiceContext serviceContext = null;

    /** Netty channel handler context */
    private ChannelHandlerContext ctx = null;

    /** Byte buffer */
    private ByteBuf byteBuf = null;

    /** Attribute map */
    private Map<String,Object> attributeMap = null;

    public DefaultSockRequest(ServiceContext serviceContext, ChannelHandlerContext ctx, ByteBuf byteBuf) {
        this.serviceContext = serviceContext;
        this.ctx = ctx;
        this.byteBuf = byteBuf;
    }

    /**
     * Get byte buffer of request body
     * @return
     */
    @Override
    public ByteBuf getRequestBody() {
        return this.byteBuf;
    }

    /**
     * Set request attribute
     * @param name attribute name
     * @param o attribute value
     */
    @Override
    public void setAttribute(String name, Object o) {
        if (this.attributeMap == null) {
            //Not thread safe
            this.attributeMap = new HashMap<>(6);
        }
        this.attributeMap.put(name, o);
    }

    /**
     * Get request attribute
     * @param name attribute name
     * @return attribute value
     */
    @Override
    public Object getAttribute(String name) {
        if (this.attributeMap == null || this.attributeMap.size() <= 0) {
            return null;
        }
        else {
            return this.attributeMap.get(name);
        }
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
    public SocketAddress getLocalAddr() {
        return this.ctx.channel().localAddress();
    }

    @Override
    public SocketAddress getRemoteAddr() {
        return this.ctx.channel().remoteAddress();
    }

    @Override
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }
}
