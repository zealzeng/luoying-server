package com.luoying.server;

import com.luoying.ioc.ApplicationContext;
import com.luoying.ioc.DisposableBean;

/**
 * GenericApplication extracts some common method
 * Created by Zeal on 2018/10/22 0022.
 */
public abstract class GenericService implements Service {

    protected ConcurrentServiceContext serviceContext = new ConcurrentServiceContext();

    @Override
    public void initialize() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
        ApplicationContext applicationContext = this.getServiceContext().getApplicationContext();
        String[] beanNames = applicationContext.getBeanNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            if (bean == null || !(bean instanceof DisposableBean)) {
                continue;
            }
            try {
                ((DisposableBean) bean).destroy();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get service context
     * @return
     */
    public ServiceContext getServiceContext() {
        return this.serviceContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.serviceContext.setApplicationContext(applicationContext);
    }


}
