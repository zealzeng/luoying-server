package com.whlylc.server.sock;

import com.whlylc.server.ChannelService;
import com.whlylc.server.ChannelServiceHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class DefaultSockChannelService extends ChannelService<SocketChannel> {

    public DefaultSockChannelService(int port, SockService application) {
        super(port, application);
    }

    /**
     * Before initialize channel
     *
     * @param ch
     */
    public void beforeInitChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
    }


    @Override
    public ChannelServiceHandler createChannelServiceHandler() {
        return new DefaultSockServerHandler((SockService) this.getService());
    }
}
