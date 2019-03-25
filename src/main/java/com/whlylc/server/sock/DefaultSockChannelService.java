package com.whlylc.server.sock;

import com.whlylc.server.ChannelService;
import com.whlylc.server.ChannelServiceHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
@ChannelHandler.Sharable
public class DefaultSockChannelService extends ChannelService<SocketChannel,SockService> {

    private LengthFieldPrepender lengthFieldPrepender = new LengthFieldPrepender(4);

    public DefaultSockChannelService(int port, SockService application) {
        super(port, application);
    }

    /**
     * Before initialize channel
     *
     * @param ch
     */
    public void beforeInitChannel(SocketChannel ch) {
        super.beforeInitChannel(ch);
        ChannelPipeline pipeline = ch.pipeline();
        //Not sharable
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        //Sharable
        pipeline.addLast(lengthFieldPrepender);
        //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
    }


    @Override
    public ChannelServiceHandler<SockService> createChannelServiceHandler() {
        return new DefaultSockServerHandler(this.getService());
    }
}
