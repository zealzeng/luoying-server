package com.whlylc.server;

import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.Executor;

/**
 * The handler should be sharable
 * Created by Zeal on 2019/3/20 0020.
 */
public abstract class ChannelServiceInboundHandler<S extends Service,C extends ServiceConnection,RQ extends ServiceRequest,RP extends ServiceResponse,T> extends SimpleChannelInboundHandler<T> implements ChannelServiceHandler<S> {

    private static AttributeKey CONNECTION_ATTR_KEY = AttributeKey.valueOf("sock_connection_key");

    protected ServerContext serverContext = null;

    //Like servlet
    protected S service = null;

    public ChannelServiceInboundHandler(ServerContext serverContext, S service) {
        super(false);
        this.serverContext = serverContext;
        this.service = service;
    }

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
        Executor executor = this.getServerContext().getWorkerPoolExecutor();
        try {
            executor.execute(new WorkerTask(ctx, msg, request, response));
        }
        //Default reject policy is abort
        catch (Throwable t) {
            ctx.fireExceptionCaught(t);
        }
    }

    protected class WorkerTask implements Runnable {
        private ChannelHandlerContext ctx = null;
        private T msg = null;
        private RQ request = null;
        private RP response = null;
        public WorkerTask(ChannelHandlerContext ctx, T msg, RQ request, RP response) {
            this.ctx = ctx;
            this.msg = msg;
            this.request = request;
            this.response = response;
        }
        @Override
        public void run() {
            try {
                service.service(request, response);
            } catch (Throwable e) {
                ctx.fireExceptionCaught(e);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
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
    }

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

    public ServerContext getServerContext() {
        return this.serverContext;
    }
}
