package com.whlylc.server;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.DisposableBean;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * GenericApplication extracts some common method
 * Created by Zeal on 2018/10/22 0022.
 */
public abstract class GenericService<C extends ServiceConnection,RQ extends ServiceRequest, RP extends ServiceResponse> implements Service<C,RQ,RP> {

    protected ConcurrentServiceContext serviceContext = new ConcurrentServiceContext();

    protected ApplicationContext applicationContext = null;

    @Override
    public void initialize() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
//        ApplicationContext applicationContext = this.getApplicationContext();
//        String[] beanNames = applicationContext.getBeanNames();
//        for (String beanName : beanNames) {
//            Object bean = applicationContext.getBean(beanName);
//            if (bean == null || !(bean instanceof DisposableBean)) {
//                continue;
//            }
//            try {
//                ((DisposableBean) bean).destroy();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * Get service context
     *
     * @return
     */
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        //this.ser(applicationContext);
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public void exceptionCaught(C connection, Throwable cause) throws Exception {
        //Do nothing
    }

    @Override
    public void userEventTriggered(C connection, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    handleReaderIdle(connection);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(connection);
                    break;
                case ALL_IDLE:
                    handleAllIdle(connection);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Reading timeout
     * @param ctx
     */
    protected void handleReaderIdle(C ctx) {
        ctx.close();
    }

    /**
     * Writing timeout
     * @param ctx
     */
    protected void handleWriterIdle(C ctx) {
        //Do nothing
    }

    /**
     * All timeout
     * @param ctx
     */
    protected void handleAllIdle(C ctx) {
        //Do nothing
    }

    /**
     * Client close the connection
     * @param connection
     * @throws Exception
     */
    @Override
    public void channelInactive(C connection) throws Exception {
        connection.close();
    }
}
