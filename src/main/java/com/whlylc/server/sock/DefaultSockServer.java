package com.whlylc.server.sock;

import com.whlylc.server.ChannelService;
import com.whlylc.server.MultiChannelServer;

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
