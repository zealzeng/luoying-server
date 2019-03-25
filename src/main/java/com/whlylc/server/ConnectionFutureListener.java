package com.whlylc.server;

/**
 * Created by Zeal on 2019/3/25 0025.
 */
public interface ConnectionFutureListener {

//    private ConnectionFuture connectionFuture = null;
//
//    public ConnectionFutureListener(ConnectionFuture connectionFuture) {
//        this.connectionFuture = connectionFuture;
//    }

    void complete(ConnectionFuture connectionFuture);

}
