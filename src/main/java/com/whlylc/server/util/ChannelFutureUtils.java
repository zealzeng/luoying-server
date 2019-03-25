package com.whlylc.server.util;

import com.whlylc.server.sock.SockResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by Zeal on 2019/3/25 0025.
 */
public class ChannelFutureUtils {

    public static void writeFutureListener(ChannelFuture future, int futureAction) {
//        if (futureAction == SockResponse.CLOSE) {
//            future.addListener(ChannelFutureListener.CLOSE);
//        }
//        else if (futureAction == SockResponse.CLOSE_ON_FAILURE) {
//            future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        }
//        else if (futureAction == SockResponse.FIRE_EXCEPTION_ON_FAILURE) {
//            future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
//        }
    }

}
