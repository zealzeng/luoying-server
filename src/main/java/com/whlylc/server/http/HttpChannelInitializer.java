package com.whlylc.server.http;

import com.whlylc.server.ChannelServiceHandler;
import com.whlylc.server.ProtocolChannelInitializer;
import com.whlylc.server.Service;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Zeal
 */
public class HttpChannelInitializer extends ProtocolChannelInitializer<HttpServerOptions,Channel> {

    public HttpChannelInitializer(HttpServerOptions serverOptions, Service service) {
        super(serverOptions, service);
    }

    @Override
    protected ChannelServiceHandler createChannelServiceHandler() {
        return new DefaultHttpServerHandler();
    }

    @Override
    protected void beforeInitChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        //Max content-length is less than 2 MB
        //FIXME Extract parameter into HttpServerOptions
        pipeline.addLast(new HttpObjectAggregator(2 *1024 * 1024));
    }
}
