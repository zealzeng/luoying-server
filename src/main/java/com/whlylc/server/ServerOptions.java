package com.whlylc.server;

import io.netty.channel.ServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

/**
 * Server configuration parameters
 * @author Zeal
 */
public class ServerOptions {

    public static final String DEFAULT_HOST = "0.0.0.0";

    private int port = 0 ;

    private String host = DEFAULT_HOST;

    private int acceptorThreads = NettyRuntime.availableProcessors() * 2;

    //Not worker thread, never block event loop thread
    private int eventLoopThreads = NettyRuntime.availableProcessors() * 5;

    private int maxWorkerPoolThreads = NettyRuntime.availableProcessors() * 10;

    private int maxWorkerPoolQueueSize = 1000;

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
}
