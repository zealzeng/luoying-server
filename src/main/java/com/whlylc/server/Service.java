package com.whlylc.server;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.ApplicationContextAware;
import com.whlylc.ioc.DisposableBean;
import com.whlylc.ioc.InitializingBean;

/**
 * Business service extraction, singleton service is recommended .
 * It's a bit like servlet and make sure the service() is stateless
 * Created by Zeal on 2018/10/21 0021.
 */
public interface Service<C extends ServiceConnection,RQ extends ServiceRequest, RP extends ServiceResponse> extends ApplicationContextAware,InitializingBean, DisposableBean {

    /**
     * Main service method
     * @param request
     * @param response
     * @throws Exception
     */
    void service(RQ request, RP response) throws Exception;

    /**
     * Get service context
     * @return
     */
    ServiceContext getServiceContext();

        /**
     * Handle exception
     * @param connection
     * @param cause
     * @throws Exception
     */
    void exceptionCaught(C connection, Throwable cause) throws Exception;

    /**
     * User event like read,write timeout
     * @param connection
     * @param evt
     * @throws Exception
     */
    void userEventTriggered(C connection, Object evt) throws Exception;

    /**
     * Channel is inactive, might be closed by client
     * @param connection
     * @throws Exception
     */
    void channelInactive(C connection) throws Exception;

}
