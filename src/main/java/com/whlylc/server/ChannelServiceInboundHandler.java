package com.whlylc.server;

import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by Zeal on 2019/3/20 0020.
 */
public abstract class ChannelServiceInboundHandler<S extends Service,C extends ServiceConnection,RQ extends ServiceRequest,RP extends ServiceResponse,T> extends SimpleChannelInboundHandler<T> implements ChannelServiceHandler<S> {

    private static AttributeKey CONNECTION_ATTR_KEY = AttributeKey.valueOf("sock_connection_key");

    //Like servlet
    protected S service = null;

    /**
     * Get or create service connection
     * @param ctx
     * @return
     */
    protected C getConnection(ChannelHandlerContext ctx) {
        Attribute attr = ctx.channel().attr(CONNECTION_ATTR_KEY);
        C connection = (C) attr.get();
        if (connection == null) {
            connection = this.createConnection(ctx);
            attr.setIfAbsent(connection);
        }
        return connection;
    }

    /**
     * Create service connection
     * @param ctx
     * @return
     */
    protected abstract C createConnection(ChannelHandlerContext ctx);

    /**
     * Create request
     * @param ctx
     * @param msg
     * @return
     */
    protected abstract RQ createRequest(ChannelHandlerContext ctx, C connection, T msg);

    /**
     * Create response
     * @param ctx
     * @param connection
     * @param msg
     * @return
     */
    protected abstract RP createResponse(ChannelHandlerContext ctx, C connection, T msg);

    /**
     * <strong>Please keep in mind that this method will be renamed to
     * {@code messageReceived(ChannelHandlerContext, I)} in 5.0.</strong>
     *
     * Is called for each message of type {@link T}.
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception    is thrown if an error occurred
     */
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        C connection = this.getConnection(ctx);
        RQ request = this.createRequest(ctx, connection, msg);
        RP response = this.createResponse(ctx, connection, msg);
        service.service(request, response);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        C connection = this.getConnection(ctx);
        service.exceptionCaught(connection, cause);
        ctx.fireExceptionCaught(cause);
    }



    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        service.userEventTriggered(this.getConnection(ctx), evt);
        ctx.fireUserEventTriggered(evt);
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            switch (event.state()) {
//                case READER_IDLE:
//                    handleReaderIdle(ctx);
//                    break;
//                case WRITER_IDLE:
//                    handleWriterIdle(ctx);
//                    break;
//                case ALL_IDLE:
//                    handleAllIdle(ctx);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

//    /**
//     * Reading timeout
//     * @param ctx
//     */
//    protected void handleReaderIdle(ChannelHandlerContext ctx) {
//        ctx.close();
//    }
//
//    /**
//     * Writing timeout
//     * @param ctx
//     */
//    protected void handleWriterIdle(ChannelHandlerContext ctx) {
//    }
//
//    /**
//     * All timeout
//     * @param ctx
//     */
//    protected void handleAllIdle(ChannelHandlerContext ctx) {
//    }

        /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        service.channelInactive(this.getConnection(ctx));
        ctx.fireChannelInactive();
    }

    @Override
    public S getService() {
        return service;
    }

    @Override
    public void setService(S service) {
        this.service = service;
    }
}
