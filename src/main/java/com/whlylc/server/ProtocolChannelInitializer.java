package com.whlylc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author Zeal
 */
@ChannelHandler.Sharable
public abstract class ProtocolChannelInitializer<S extends Service,C extends Channel> extends ChannelInitializer<C> {

    //protected SO serverOptions = null;
    protected ServerContext serverContext = null;

    protected S service = null;

    protected ChannelServiceHandler channelServiceHandler = null;

    public ProtocolChannelInitializer(ServerContext serverContext, S service) {
        this.serverContext = serverContext;
        this.service = service;
        this.channelServiceHandler = this.createChannelServiceHandler();
        //this.channelServiceHandler.setService(service);
    }

    protected abstract ChannelServiceHandler createChannelServiceHandler();

    protected abstract void beforeInitChannel(C ch) throws Exception;

    @Override
    protected void initChannel(C ch) throws Exception {
        this.beforeInitChannel(ch);
        if (this.channelServiceHandler != null) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(this.channelServiceHandler);
        }
        this.afterInitChannel(ch);
    }

    protected void afterInitChannel(C ch) throws Exception {
    }
}
