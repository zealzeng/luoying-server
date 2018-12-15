package com.luoying.server.sock;

import com.luoying.server.ChannelService;
import com.luoying.server.MultiChannelServer;

/**
 * Default socket server with LengthFieldBasedFrameDecoder
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockServer extends MultiChannelServer {

    public DefaultSockServer(int port, SockService application) {
        DefaultSockChannelService channelApplication = new DefaultSockChannelService(port, application);
        this.setChannelApplications(new ChannelService[] {channelApplication});
    }

}
