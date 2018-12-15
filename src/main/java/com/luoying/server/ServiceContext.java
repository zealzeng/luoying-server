package com.luoying.server;

import com.luoying.ioc.ApplicationContext;

/**
 * Service context
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ServiceContext extends AttributeAware {

    /**
     * Get application context and bean factory
     * @return
     */
    ApplicationContext getApplicationContext();

}
