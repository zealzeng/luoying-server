package com.whlylc.server;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by Zeal on 2019/3/20 0020.
 */
public abstract class ChannelServiceInboundHandler<T,S extends Service> extends SimpleChannelInboundHandler<T> implements ChannelServiceHandler<S> {

        /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        //ctx.close();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Reading timeout
     * @param ctx
     */
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        ctx.close();
    }

    /**
     * Writing timeout
     * @param ctx
     */
    protected void handleWriterIdle(ChannelHandlerContext ctx) {
    }

    /**
     * All timeout
     * @param ctx
     */
    protected void handleAllIdle(ChannelHandlerContext ctx) {
    }



}
