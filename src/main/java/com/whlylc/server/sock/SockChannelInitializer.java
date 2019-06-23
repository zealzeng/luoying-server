package com.whlylc.server.sock;

import com.whlylc.server.ChannelServiceHandler;
import com.whlylc.server.ProtocolChannelInitializer;
import com.whlylc.server.ServerContext;
import com.whlylc.server.Service;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Zeal
 */
public class SockChannelInitializer extends ProtocolChannelInitializer<SockService, Channel> {

    private LengthFieldPrepender lengthFieldPrepender = new LengthFieldPrepender(4);

    public SockChannelInitializer(ServerContext serverContext, SockService service) {
        super(serverContext, service);
    }

    @Override
    protected ChannelServiceHandler createChannelServiceHandler() {
        return new DefaultSockServerHandler(this.serverContext, this.service);
    }

    @Override
    protected void beforeInitChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //Not sharable
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        //Sharable
        pipeline.addLast(lengthFieldPrepender);

    }
}
