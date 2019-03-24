package com.whlylc.server.sock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.HashSet;
import java.util.Set;

/**
 * By default, one session only can have one connection
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultSockSession implements SockSession {

    private SockConnection connection = null;

    private Set<SockConnection> connections = null;

    private ChannelHandlerContext ctx = null;

    public DefaultSockSession(ChannelHandlerContext ctx, SockConnection connection) {
        this.ctx = ctx;
        this.connection = connection;
        Set<SockConnection> connections = new HashSet<>(1);
        connections.add(this.connection);
        this.connections = connections;
    }

    @Override
    public void invalidate() {
        this.connection.close();
    }

    @Override
    public Set<SockConnection> getConnections() {
        return this.connections;
    }

    @Override
    public void setAttribute(String name, Object o) {
        AttributeKey<Object> key = AttributeKey.valueOf(name);
        Attribute<Object> attr = ctx.channel().attr(key);
        attr.set(o);
    }

    @Override
    public Object getAttribute(String name) {
        AttributeKey<Object> key = AttributeKey.valueOf(name);
        Attribute<Object> attr = ctx.channel().attr(key);
        return attr.get();
    }

}
