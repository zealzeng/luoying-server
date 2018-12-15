package com.luoying.server.http;

import com.luoying.server.ChannelService;
import com.luoying.server.MultiChannelServer;

/**
 * Default http server
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultHttpServer extends MultiChannelServer {

    public DefaultHttpServer(int port, HttpService application) {
        DefaultHttpChannelService channelApplication = new DefaultHttpChannelService(port, application);
        this.setChannelApplications(new ChannelService[] {channelApplication});
    }


}
