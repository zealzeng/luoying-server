package com.whlylc.server;

import com.whlylc.ioc.ApplicationContext;

/**
 * Service context, like servlet context
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ServiceContext extends AttributeAware {

    /**
     * Get application context and bean factory
     * @return
     */
    ApplicationContext getApplicationContext();

}
