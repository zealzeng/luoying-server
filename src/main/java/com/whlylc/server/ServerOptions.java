package com.whlylc.server;

import io.netty.channel.ServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

import java.util.concurrent.TimeUnit;

/**
 * Server configuration parameters
 * @author Zeal
 */
public class ServerOptions {

    public static final String DEFAULT_HOST = "0.0.0.0";

    public static final boolean DEFAULT_REUSE_ADDRESS = true;

    public static final boolean DEFAULT_TCP_NO_DELAY = true;

    public static final boolean DEFAULT_TCP_KEEP_ALIVE = false;

    private int port = 0 ;

    private String host = DEFAULT_HOST;

    private boolean reuseAddress = DEFAULT_REUSE_ADDRESS;

    private boolean tcpNoDelay = DEFAULT_TCP_NO_DELAY;

    private boolean tcpKeepAlive = DEFAULT_TCP_KEEP_ALIVE;

    private int acceptorThreads = NettyRuntime.availableProcessors() * 2;

    //Not worker thread, never block event loop thread
    private int eventLoopThreads = NettyRuntime.availableProcessors() * 5;

    private int maxWorkerPoolThreads = NettyRuntime.availableProcessors() * 10;

    private int maxWorkerPoolQueueSize = 1000;

    private long readerIdleTime = 0;

    private long writerIdleTime = 0;

    private long allIdleTime = 0;

    private TimeUnit idleTimeUnit = TimeUnit.MILLISECONDS;

    //Server channel class name
    private Class<? extends ServerChannel> serverChannelClass = NioServerSocketChannel.class;

    public ServerOptions(int port) {
        setPort(port);
    }

    public int getPort() {
        return port;
    }

    public ServerOptions setPort(int port) {
        if (port <= 0 || port > 65535) {
            throw new IllegalStateException("Invalid server port " + port);
        }
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public ServerOptions setHost(String host) {
        this.host = host;
        return this;
    }

    public int getAcceptorThreads() {
        return acceptorThreads;
    }

    public ServerOptions setAcceptorThreads(int acceptorThreads) {
        this.acceptorThreads = acceptorThreads;
        return this;
    }

    public int getEventLoopThreads() {
        return eventLoopThreads;
    }

    public ServerOptions setEventLoopThreads(int eventLoopThreads) {
        this.eventLoopThreads = eventLoopThreads;
        return this;
    }

    public Class<? extends ServerChannel> getServerChannelClass() {
        return serverChannelClass;
    }

    public ServerOptions setServerChannelClass(Class<? extends ServerChannel> serverChannelClass) {
        this.serverChannelClass = serverChannelClass;
        return this;
    }

    public int getMaxWorkerPoolThreads() {
        return maxWorkerPoolThreads;
    }

    public ServerOptions setMaxWorkerPoolThreads(int maxWorkerPoolThreads) {
        this.maxWorkerPoolThreads = maxWorkerPoolThreads;
        return this;
    }

    public int getMaxWorkerPoolQueueSize() {
        return maxWorkerPoolQueueSize;
    }

    public ServerOptions setMaxWorkerPoolQueueSize(int maxWorkerPoolQueueSize) {
        this.maxWorkerPoolQueueSize = maxWorkerPoolQueueSize;
        return this;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public ServerOptions setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
        return this;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public ServerOptions setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
        return this;
    }

    public boolean isTcpKeepAlive() {
        return tcpKeepAlive;
    }

    public ServerOptions setTcpKeepAlive(boolean tcpKeepAlive) {
        this.tcpKeepAlive = tcpKeepAlive;
        return this;
    }

    public long getReaderIdleTime() {
        return readerIdleTime;
    }

    public ServerOptions setReaderIdleTime(long readerIdleTime) {
        this.readerIdleTime = readerIdleTime;
        return this;
    }

    public long getWriterIdleTime() {
        return writerIdleTime;
    }

    public ServerOptions setWriterIdleTime(long writerIdleTime) {
        this.writerIdleTime = writerIdleTime;
        return this;
    }

    public long getAllIdleTime() {
        return allIdleTime;
    }

    public ServerOptions setAllIdleTime(long allIdleTime) {
        this.allIdleTime = allIdleTime;
        return this;
    }

    public TimeUnit getIdleTimeUnit() {
        return idleTimeUnit;
    }

    public ServerOptions setIdleTimeUnit(TimeUnit idleTimeUnit) {
        this.idleTimeUnit = idleTimeUnit;
        return this;
    }
}
