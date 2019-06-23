package com.whlylc.server.http;

import com.whlylc.server.ChannelServiceInboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Zeal on 2018/9/15 0015.
 */
@ChannelHandler.Sharable
public class DefaultHttpServerHandler extends ChannelServiceInboundHandler<HttpService,HttpConnection,HttpRequest,HttpResponse,FullHttpRequest> {

    @Override
    protected HttpConnection createConnection(ChannelHandlerContext ctx) {
        return new DefaultHttpConnection(service.getServiceContext(), ctx);
    }

    @Override
    protected HttpRequest createRequest(ChannelHandlerContext ctx, HttpConnection connection, FullHttpRequest msg) {
        return new DefaultHttpRequest(ctx, connection, msg);
    }

    @Override
    protected HttpResponse createResponse(ChannelHandlerContext ctx, HttpConnection connection, FullHttpRequest msg) {
        return new DefaultHttpResponse(ctx, connection, msg);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        HttpConnection connection = this.createConnection(ctx);
        HttpRequest _request = this.createRequest(ctx, connection, request);
        HttpResponse _response = this.createResponse(ctx, connection, request);
        try {
            this.service.service(_request, _response);
        }
        finally {
            _response.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

//    @Override
//    public HttpService getService() {
//        return this.httpService;
//    }
//
//    @Override
//    public void setService(HttpService service) {
//        this.httpService = service;
//    }

}
