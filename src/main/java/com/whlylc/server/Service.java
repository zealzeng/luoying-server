package com.whlylc.server;

import com.whlylc.ioc.DisposableBean;
import com.whlylc.ioc.InitializingBean;

/**
 * Business service extraction, singleton service is recommended .
 * It's a bit like servlet and make sure the service() is stateless
 * Created by Zeal on 2018/10/21 0021.
 */
public interface Service extends InitializingBean, DisposableBean {

    /**
     * Main service method
     * @param request
     * @param response
     * @throws Exception
     */
    void service(ServiceRequest request, ServiceResponse response) throws Exception;


    /**
     * Get service context
     * @return
     */
    ServiceContext getServiceContext();
}
