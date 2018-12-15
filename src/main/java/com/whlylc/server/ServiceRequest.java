package com.whlylc.server;

/**
 * Service request
 * Created by Zeal on 2018/10/21 0021.
 */
public interface ServiceRequest extends AttributeAware {

    /**
     * Get session
     * @param create true - create session is it's not present
     * @return
     */
    ServiceSession getSession(boolean create);

    /**
     * Get service context
     * @return
     */
    ServiceContext getServiceContext();

}
