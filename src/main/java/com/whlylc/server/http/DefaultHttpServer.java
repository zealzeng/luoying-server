package com.whlylc.server.http;

import com.whlylc.server.*;
import io.netty.channel.ChannelInitializer;

/**
 * Default http server
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultHttpServer extends NettyServer<HttpServerOptions,HttpService> implements HttpServer {

    public DefaultHttpServer(HttpService service) {
        super(new HttpServerOptions(80), service);
    }

    public DefaultHttpServer(int port, HttpService service) {
        super(new HttpServerOptions(port), service);
    }

    public DefaultHttpServer(HttpServerOptions serverOptions, HttpService service) {
        super(serverOptions, service);
    }

    @Override
    protected ChannelInitializer createChannelInitializer() {
        return new HttpChannelInitializer(this.serverContext, this.service);
    }


}
