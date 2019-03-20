package com.whlylc.server;

import io.netty.channel.ChannelHandler;

/**
 * Create handler instance for each request
 * Created by Zeal on 2018/10/21 0021.
 */
public interface ChannelServiceHandler<S extends Service> extends ChannelHandler {

    S getService();

    void setService(S service);

}
