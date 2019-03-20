package com.whlylc.server.http;

import com.whlylc.server.ChannelService;
import com.whlylc.server.ChannelServiceHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultHttpChannelService extends ChannelService<SocketChannel,HttpService> {

    public DefaultHttpChannelService(int port, HttpService application) {
        super(port, application);
    }

    /**
     * Before initialize channel
     * @param ch
     */
    public void beforeInitChannel(SocketChannel ch) {
        super.beforeInitChannel(ch);
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        //Max content-length is less than 2 MB
        pipeline.addLast(new HttpObjectAggregator(2 *1024 * 1024));
    }

    /**
     * Create handler for each request
     * @return
     */
    public ChannelServiceHandler<HttpService> createChannelServiceHandler() {
        return new DefaultHttpServerHandler(this.getService());
    }


}
