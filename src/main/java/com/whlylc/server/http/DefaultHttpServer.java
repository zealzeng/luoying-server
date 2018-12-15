package com.whlylc.server.http;

import com.whlylc.server.ChannelService;
import com.whlylc.server.MultiChannelServer;

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
