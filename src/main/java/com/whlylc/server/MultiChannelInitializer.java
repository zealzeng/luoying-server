package com.whlylc.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Multiple channel initializer
 * Created by Zeal on 2018/10/21 0021.
 * @deprecated 
 */
public class MultiChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultiChannelInitializer.class);

    private Map<Integer,ChannelService> channelApplicationMap = null;

    public MultiChannelInitializer(ChannelService[] applications) throws Exception {
        this.channelApplicationMap = new HashMap<>(applications.length);
        for (ChannelService application : applications) {
            this.channelApplicationMap.put(application.getChannelPort(), application);
        }
    }

    /**
     * Every request will enter here to initialize socket channel
     * @param ch
     */
    @Override
    public void initChannel(SocketChannel ch) {
        int port = ch.localAddress().getPort();
        ChannelService application = this.channelApplicationMap.get(port);
        if (application == null) {
            //FIXME Warning
        }
        else {
            application.initChannel(ch);
        }
    }

}
