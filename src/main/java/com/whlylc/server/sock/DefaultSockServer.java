package com.whlylc.server.sock;

import com.whlylc.server.NettyServer;
import com.whlylc.server.Service;
import io.netty.channel.ChannelInitializer;

/**
 * Default socket server with LengthFieldBasedFrameDecoder
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockServer extends NettyServer<SockServerOptions,SockService> implements SockServer {

    public DefaultSockServer(int port, SockService service) {
        super(new SockServerOptions(port), service);
    }

    public DefaultSockServer(SockServerOptions serverOptions, SockService service) {
        super(serverOptions, service);
    }

    @Override
    protected ChannelInitializer createChannelInitializer() {
        return new SockChannelInitializer(this.serverContext, service);
    }

}
