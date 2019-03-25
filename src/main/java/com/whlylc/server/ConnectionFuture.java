package com.whlylc.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Wrapper class for ServiceConnection and ChannelFuture
 * Created by Zeal on 2019/3/25 0025.
 */
public class ConnectionFuture<C extends ServiceConnection> {

    private C connection = null;

    private ChannelFuture channelFuture = null;

    public ConnectionFuture(C connection, ChannelFuture channelFuture) {
        this.connection = connection;
        this.channelFuture = channelFuture;
    }

    public Void get() throws ExecutionException, InterruptedException {
        return this.channelFuture.get();
    }

    public Void get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.channelFuture.get(timeout, timeUnit);
    }
    public boolean isVoid() {
        return this.channelFuture.isVoid();
    }

    public void await() throws InterruptedException {
        this.channelFuture.wait();
    }

    public void await(long timeout) throws InterruptedException {
        this.channelFuture.await(timeout);
    }

    public void await(long timeout, TimeUnit timeUnit) throws InterruptedException {
        this.channelFuture.await(timeout, timeUnit);
    }

    public C getConnection() {
        return connection;
    }

    public boolean cancel(boolean interupt) {
        return this.channelFuture.cancel(interupt);
    }

    public boolean isCancellable() {
        return this.channelFuture.isCancellable();
    }

    /**
     * Returns {@code true} if and only if the I/O operation was completed
     * successfully.
     */
    public boolean isSuccess() {
        return this.channelFuture.isSuccess();
    }

    public boolean isCancelled() {
        return this.channelFuture.isCancelled();
    }

    /**
     * Returns {@code true} if this task completed.
     *
     * Completion may be due to normal termination, an exception, or
     * cancellation -- in all of these cases, this method will return
     * {@code true}.
     *
     * @return {@code true} if this task completed
     */
    public boolean isDone() {
        return this.channelFuture.isDone();
    }

    public void addListener(ConnectionFutureListener<C> listener) {
        this.channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                listener.complete(ConnectionFuture.this);
            }
        });
    }








}
