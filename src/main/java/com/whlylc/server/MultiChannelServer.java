package com.whlylc.server;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.DisposableBean;
import com.whlylc.ioc.InitializingBean;
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
 * @deprecated
 */
public class MultiChannelServer {

    //Logger
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultiChannelServer.class);

    protected ChannelService[] channelApplications = null;

    protected int bossGroupThreads = NettyRuntime.availableProcessors() * 2;

    protected int workerGroupThreads = NettyRuntime.availableProcessors() * 10;

    ServerBootstrap serverBootstrap = null;

    protected Set<Service> services = null;

    protected Set<ApplicationContext> applicationContexts = null;

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

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(this.bossGroupThreads);
        //FIXME Use JDK Executor later
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(this.workerGroupThreads);
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(this.logLevel)).childHandler(new MultiChannelInitializer(this.channelApplications));

            services = new HashSet<>(this.channelApplications.length);
            for (int i = 0; i < channelApplications.length; ++i) {
                ChannelService channelApplication = channelApplications[i];
                Service service = channelApplication.getService();
                //Initialize application
                if (!services.contains(service)) {
                    services.add(service);
                }
            }


//            //Service binding and listening
//            this.bindChannelServices();

//            services = new HashSet<>(this.channelApplications.length);
//
//            //FIXME We should initialize application context -> service -> open server port??
//
//            //Bind listening port
//            for (int i = 0; i < channelApplications.length; ++i) {
//                ChannelService channelApplication = channelApplications[i];
//                Service service = channelApplication.getService();
//                Channel channel = b.bind(channelApplication.getChannelPort()).sync().channel();
//                channelApplication.setChannel(channel);
//                //Initialize application
//                if (!services.contains(service)) {
//                    //service.initialize();
//                    services.add(service);
//                }
//            }

            this.initializeApplicationContexts();

//            //FIXME Optimize the codes in this class while having time
//            //Initialize application contexts
//            applicationContexts = new HashSet<>(services.size());
//            for (Service service : services) {
//                ApplicationContext applicationContext = service.getServiceContext().getApplicationContext();
//                if (applicationContext != null) {
//                    applicationContexts.add(applicationContext);
//                }
//            }
//            for (ApplicationContext applicationContext : applicationContexts) {
//                for (String beanName : applicationContext.getBeanNames()) {
//                    Object bean = applicationContext.getBean(beanName);
//                    if (bean != null && (bean instanceof InitializingBean)) {
//                        try {
//                            ((InitializingBean) bean).initialize();
//                        } catch (Throwable t) {
//                            logger.error("Failed to initialize bean " + beanName, t);
//                        }
//                    }
//                }
//            }

            initializeServices();

//            //Initialize services
//            for (Service service : services) {
//                try {
//                    service.initialize();
//                } catch (Throwable t) {
//                    logger.error("Failed to initialize service " + service, t);
//                }
//            }

            //Service binding and listening
            this.bindChannelServices();

            //Shutdown hook
            addShutdownHook();
            if (logger.isInfoEnabled()) {
                logger.info("======Server is started======");
            }
            //ch.closeFuture().sync();
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
//        services = new HashSet<>(this.channelApplications.length);
        //FIXME We should initialize application context -> service -> open server port??
        //Bind listening port
        for (int i = 0; i < channelApplications.length; ++i) {
            ChannelService channelApplication = channelApplications[i];
//            Service service = channelApplication.getService();
            Channel channel = serverBootstrap.bind(channelApplication.getChannelPort()).sync().channel();
            if (logger.isInfoEnabled()) {
                logger.info("Server is listening on " + channelApplication.getChannelPort());
            }
            channelApplication.setChannel(channel);
//            //Initialize application
//            if (!services.contains(service)) {
//                //service.initialize();
//                services.add(service);
//            }
        }
    }

    /**
     * Initialize application contexts
     */
    protected void initializeApplicationContexts() {
        //FIXME Optimize the codes in this class while having time
        //Initialize application contexts
        applicationContexts = new HashSet<>(services.size());
        for (Service service : services) {
            ApplicationContext applicationContext = service.getApplicationContext();
            if (applicationContext != null) {
                applicationContexts.add(applicationContext);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("Start to initialize application context");
        }
        for (ApplicationContext applicationContext : applicationContexts) {
            for (String beanName : applicationContext.getBeanNames()) {
                Object bean = applicationContext.getBean(beanName);
                if (bean != null && (bean instanceof InitializingBean)) {
                    try {
                        ((InitializingBean) bean).initialize();
                        if (logger.isInfoEnabled()) {
                            logger.info("Bean " + bean.getClass() + " is initialized");
                        }
                    } catch (Throwable t) {
                        logger.error("Failed to initialize bean " + beanName, t);
                    }
                }
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("Finish to initialize application context");
        }
    }

    /**
     * Initialize services
     */
    protected void initializeServices() {
        if (services == null || services.size() <= 0) {
            throw new IllegalStateException("Service is required");
        }
        if (logger.isInfoEnabled()) {
            logger.info("Start to initialize service");
        }
        //Initialize services
        for (Service service : services) {
            try {
                service.initialize();
                if (logger.isInfoEnabled()) {
                    logger.info("Service " + service.getClass() + " is initialized");
                }
            } catch (Throwable t) {
                logger.error("Failed to initialize service " + service, t);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("Finish to initialize service");
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

    private void destroyApplicationContexts() {
        if (applicationContexts != null && applicationContexts.size() > 0) {
            for (ApplicationContext applicationContext : applicationContexts) {
                for (String beanNam : applicationContext.getBeanNames()) {
                    Object bean = applicationContext.getBean(beanNam);
                    if (bean != null && bean instanceof DisposableBean) {
                        try {
                            ((DisposableBean) bean).destroy();
                        } catch (Throwable t) {
                            logger.warn("Failed to destroy bean " + beanNam, t);
                        }
                    }
                }
            }
        }
    }

    private void destroyServices() {
        if (services != null && services.size() > 0) {
            for (Service application : this.services) {
                try {
                    application.destroy();
                } catch (Exception e) {
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
        this.destroyServices();
        //Destroy application contexts
        this.destroyApplicationContexts();

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
