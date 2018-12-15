package com.whlylc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * Each channel has an service, support SSL while having time
 * Created by Zeal on 2018/10/21 0021.
 */
public abstract class ChannelService<C extends Channel> {

    private int port = 0;

    private Channel channel = null;

    private Service service = null;

    public ChannelService(int port, Service application) {
        this.port = port;
        this.service = application;
    }

    public void setChannel(Channel channel) {
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

    public Service getService() {
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
        pipeline.addLast(createChannelServiceHandler());
        //After
        afterInitChannel(ch);
    }

    /**
     * Before initialize channel
     * @param ch
     */
    public void beforeInitChannel(C ch) {
    }

    /**
     * Create handler for each request
     * @return
     */
    public abstract ChannelServiceHandler createChannelServiceHandler();

    /**
     * Ater initialize channel
     * @param ch
     */
    public void afterInitChannel(C ch) {
    }

}
