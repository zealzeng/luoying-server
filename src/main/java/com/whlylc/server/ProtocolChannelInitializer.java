package com.whlylc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author Zeal
 */
@ChannelHandler.Sharable
public abstract class ProtocolChannelInitializer<SO extends ServerOptions,C extends Channel> extends ChannelInitializer<C> {

    protected SO serverOptions = null;

    protected Service service = null;

    protected ChannelServiceHandler channelServiceHandler = null;

    public ProtocolChannelInitializer(SO serverOptions, Service service) {
        this.serverOptions = serverOptions;
        this.service = service;
        this.channelServiceHandler = this.createChannelServiceHandler();
        this.channelServiceHandler.setService(service);
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
