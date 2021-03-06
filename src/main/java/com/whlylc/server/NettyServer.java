package com.whlylc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Server only has one channel and service
 */
public abstract class NettyServer<SO extends ServerOptions,S extends Service> implements Server {

    //Logger
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NettyServer.class);

    protected Channel serverChannel = null;

    //Do not share the service
    protected S service = null;

    protected ServerBootstrap serverBootstrap = null;

    protected SO serverOptions = null;

    protected ServerContext serverContext = null;

    protected LogLevel logLevel = LogLevel.INFO;

    public NettyServer(SO serverOptions, S service) {
        this.serverOptions = serverOptions;
        this.service = service;
    }

    /**
     * Startup http server
     *
     * @throws Exception
     */
    public void startup() throws Exception {

        try {
            //Initialize server context
            this.initializeServerContext();
            //Initialize server bootstrap with server options
            this.initializeServerBootstrap();
            //Initialize service first since while request comes, service should be ready
            initializeService();
            //Service binding and listening
            this.bindChannelServices();
            //Shutdown hook
            addShutdownHook();
        } catch (Throwable e) {
            shutdown();
            throw e;
        }
    }

    /**
     * Biding and listening
     *
     * @throws Exception
     */
    protected void bindChannelServices() throws Exception {
        this.serverChannel = serverBootstrap.bind(this.serverOptions.getHost(), this.serverOptions.getPort()).sync().channel();
        if (logger.isInfoEnabled()) {
            logger.info("Server is listening on " + this.serverOptions.getPort());
        }
    }

    protected void initializeServerContext() {
        this.serverContext = new DefaultServerContext(this.serverOptions);
    }

    /**
     * Initialize server bootstrap with server options
     */
    protected void initializeServerBootstrap() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(this.serverOptions.getAcceptorThreads());
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(this.serverOptions.getEventLoopThreads());
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(this.serverOptions.getServerChannelClass()).handler(new LoggingHandler(this.logLevel));
        initializeServerBootstrapOptions();
        ChannelInitializer clientChannelInitializer = this.createChannelInitializer();
        if (clientChannelInitializer != null) {
            serverBootstrap.childHandler(clientChannelInitializer);
        }
    }

    protected void initializeServerBootstrapOptions() {
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, serverOptions.isReuseAddress());
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, serverOptions.isTcpNoDelay());
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, serverOptions.isTcpKeepAlive());
    }

    /**
     * To initialize requested channel
     * @return
     */
    protected abstract ChannelInitializer createChannelInitializer();

    /**
     * Initialize service
     */
    protected void initializeService() throws Exception {
        if (this.service == null) {
            throw new IllegalStateException("Service is required");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Start to initialize service");
        }
        service.initialize();
        if (logger.isInfoEnabled()) {
            logger.info("Service " + service.getClass() + " is initialized");
        }
    }

    /**
     * Add shutdown hook
     */
    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                shutdown();
            }
        });
    }

    private void destroyServices() {
        if (this.service != null) {
            try {
                this.service.destroy();
            } catch (Exception e) {
                logger.error("Failed to destroy services", e);
            }
        }
    }

    /**
     * Shut down server
     */
    public void shutdown() {

        //Destroy services
        this.destroyServices();

        //Release threads
        if (this.serverBootstrap.config().group() != null) {
            this.serverBootstrap.config().group().shutdownGracefully();
        }
        if (this.serverBootstrap.config().childGroup() != null) {
            this.serverBootstrap.config().childGroup().shutdownGracefully();
        }
        if (logger.isInfoEnabled()) {
            logger.info("======Server is closed======");
        }
    }


}
