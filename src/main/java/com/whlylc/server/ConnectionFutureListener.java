package com.whlylc.server;

/**
 * Created by Zeal on 2019/3/25 0025.
 */
public interface ConnectionFutureListener<C extends ServiceConnection> {

//    private ConnectionFuture connectionFuture = null;
//
//    public ConnectionFutureListener(ConnectionFuture connectionFuture) {
//        this.connectionFuture = connectionFuture;
//    }

    void complete(ConnectionFuture<C> connectionFuture);

    ConnectionFutureListener CLOSE = new ConnectionFutureListener() {
        @Override
        public void complete(ConnectionFuture connectionFuture) {
            connectionFuture.getConnection().close();
        }
    };

    ConnectionFutureListener CLOSE_ON_FAILURE = new ConnectionFutureListener() {
        @Override
        public void complete(ConnectionFuture connectionFuture) {
            if (!connectionFuture.isSuccess()) {
                connectionFuture.getConnection().close();
            }
        }
    };



}
