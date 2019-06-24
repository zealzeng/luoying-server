package com.whlylc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

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
        ServerOptions serverOptions = serverContext.getServerOptions();
        if (serverOptions.getReaderIdleTime() > 0 || serverOptions.getWriterIdleTime() > 0 || serverOptions.getAllIdleTime() > 0) {
            ChannelPipeline pipeline = ch.pipeline();
            long readerIdleTime = serverOptions.getReaderIdleTime() < 0 ? 0 : serverOptions.getReaderIdleTime();
            long writerIdleTime = serverOptions.getWriterIdleTime() < 0 ? 0 : serverOptions.getWriterIdleTime();
            long allIdleTime = serverOptions.getAllIdleTime() < 0 ? 0 : serverOptions.getAllIdleTime();
            pipeline.addLast(new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, serverOptions.getIdleTimeUnit()));
        }

        if (this.channelServiceHandler != null) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(this.channelServiceHandler);
        }
        this.afterInitChannel(ch);
    }

    protected void afterInitChannel(C ch) throws Exception {
    }
}
