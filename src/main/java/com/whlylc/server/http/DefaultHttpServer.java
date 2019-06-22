package com.whlylc.server.http;

import com.whlylc.server.*;
import io.netty.channel.ChannelInitializer;

/**
 * Default http server
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultHttpServer extends NettyServer<HttpServerOptions> {

    public DefaultHttpServer(int port, Service service) {
        super(new HttpServerOptions(port), service);
    }

    public DefaultHttpServer(HttpServerOptions serverOptions, Service service) {
        super(serverOptions, service);
    }

    @Override
    protected ChannelInitializer createChannelInitializer() {
        return new HttpChannelInitializer(this.serverOptions, this.service);
    }

//    public DefaultHttpServer(int port, HttpService application) {
//        DefaultHttpChannelService channelApplication = new DefaultHttpChannelService(port, application);
//        this.setChannelApplications(new ChannelService[] {channelApplication});
//    }


}
