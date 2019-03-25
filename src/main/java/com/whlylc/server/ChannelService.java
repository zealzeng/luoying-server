package com.whlylc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Each channel has an service, support SSL while having time
 * Created by Zeal on 2018/10/21 0021.
 */
public abstract class ChannelService<C extends Channel, S extends Service> {

    private int port = 0;

    private C channel = null;

    private S service = null;

    private ChannelServiceHandler<S> channelServiceHandler = null;

    private int readerIdleTimeSeconds = 0;

    private int writerIdleTimeSeconds = 0;

    private int allIdleTimeSeconds = 0;

    public ChannelService(int port, S application) {
        this.port = port;
        this.service = application;
        this.channelServiceHandler = this.createChannelServiceHandler();
        if (this.channelServiceHandler != null && this.channelServiceHandler.getService() == null) {
            this.channelServiceHandler.setService(service);
        }
    }

    public void setChannel(C channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return this.channel;
    }

    /**
     * Each channel has a port
     * @return
     */
    public int getChannelPort() {
        return this.port;
    }

    public S getService() {
        return service;
    }


    /**
     * Init channel, each request will generate a socket channel
     * @param ch
     */
    public void initChannel(C ch) {
        //Before
        beforeInitChannel(ch);
        //Fill business handler
        ChannelPipeline pipeline = ch.pipeline();
        if (this.channelServiceHandler != null) {
            pipeline.addLast(this.channelServiceHandler);
        }
        //After
        afterInitChannel(ch);
    }

    /**
     * Before initialize channel
     * @param ch
     */
    protected void beforeInitChannel(C ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (this.readerIdleTimeSeconds > 0 || this.writerIdleTimeSeconds > 0 || this.allIdleTimeSeconds > 0) {
            pipeline.addLast(new IdleStateHandler(this.readerIdleTimeSeconds, this.writerIdleTimeSeconds, this.allIdleTimeSeconds));
        }
    }

    /**
     * The channel service handler should be sharable
     * @return
     */
    public abstract ChannelServiceHandler<S> createChannelServiceHandler();

    /**
     * Ater initialize channel
     * @param ch
     */
    public void afterInitChannel(C ch) {
    }

    public int getReaderIdleTimeSeconds() {
        return readerIdleTimeSeconds;
    }

    public void setReaderIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
    }

    public int getWriterIdleTimeSeconds() {
        return writerIdleTimeSeconds;
    }

    public void setWriterIdleTimeSeconds(int writerIdleTimeSeconds) {
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
    }

    public int getAllIdleTimeSeconds() {
        return allIdleTimeSeconds;
    }

    public void setAllIdleTimeSeconds(int allIdleTimeSeconds) {
        this.allIdleTimeSeconds = allIdleTimeSeconds;
    }
}
