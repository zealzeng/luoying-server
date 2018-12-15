package com.luoying.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Server with multiple ports/channels
 * Created by Zeal on 2018/10/21 0021.
 */
public class MultiChannelServer {

    //Logger
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultiChannelServer.class);

    protected ChannelService[] channelApplications = null;

    protected int bossGroupThreads = NettyRuntime.availableProcessors() * 2;

    protected int workerGroupThreads = NettyRuntime.availableProcessors() * 10;

    protected EventLoopGroup bossGroup = null;

    protected EventLoopGroup workerGroup = null;

    protected Set<Service> services = null;

    protected LogLevel logLevel = LogLevel.INFO;

    public MultiChannelServer() {
    }

    public MultiChannelServer(ChannelService[] channelApplications) {
        this.channelApplications = channelApplications;
    }

    public void setChannelApplications(ChannelService[] channelApplications) {
        this.channelApplications = channelApplications;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Startup http server
     *
     * @throws Exception
     */
    public void startup() throws Exception {

        bossGroup = new NioEventLoopGroup(this.bossGroupThreads);
        //FIXME Use JDK Executor later
        workerGroup = new NioEventLoopGroup(this.workerGroupThreads);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(this.logLevel)).childHandler(new MultiChannelInitializer(this.channelApplications));
            services = new HashSet<>(this.channelApplications.length);

            //Bind listening port
            for (int i = 0; i < channelApplications.length; ++i) {
                ChannelService channelApplication = channelApplications[i];
                Service service = channelApplication.getService();
                Channel channel = b.bind(channelApplication.getChannelPort()).sync().channel();
                channelApplication.setChannel(channel);
                //Initialize application
                if (!services.contains(service)) {
                    service.initialize();
                    services.add(service);
                }
            }
            //Shutdown hook
            addShutdownHook();
            if (logger.isInfoEnabled()) {
                logger.info("======Server is started======");
            }
            //ch.closeFuture().sync();
        } catch (Exception e) {
            shutdown();
            throw e;
        }
    }

    /**
     * Add shut donw hook
     */
    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                shutdown();
            }
        });
    }

    private void destroyApplicationServices() {
        if (services != null) {
            for (Service application : this.services) {
                try {
                    application.destroy();
                }
                catch (Exception e) {
                    logger.error("Failed to destroy services", e);
                }
            }
        }
    }

    /**
     * Shut down server
     */
    public void shutdown() {

        //Destroy services
        this.destroyApplicationServices();
        //Release threads
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (logger.isInfoEnabled()) {
            logger.info("======Server is closed======");
        }
    }


}