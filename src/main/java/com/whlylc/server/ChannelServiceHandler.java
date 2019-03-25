package com.whlylc.server;

import io.netty.channel.ChannelHandler;

/**
 * The channel handler should be sharable
 * Created by Zeal on 2018/10/21 0021.
 */
public interface ChannelServiceHandler<S extends Service> extends ChannelHandler {

    S getService();

    void setService(S service);

}
