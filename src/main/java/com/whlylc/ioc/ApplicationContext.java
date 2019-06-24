package com.whlylc.ioc;

/**
 * Application context, business bean factory, only support singleton bean
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ApplicationContext {

    /**
     * @param requiredType bean class
     * @return null if it's not existed, should throw exception if duplicate type in factory
     */
    <T> T getBean(Class<T> requiredType);

    /**
     * @param name bean name
     * @return null if it's not existed
     */
    Object getBean(String name);

    /**
     * Get all bean names
     * @return bean names
     */
    String[] getBeanNames();

    /**
     * Get environment
     * @return
     */
    Environment getEnvironment();

    /**
     * Release bean resources
     * @throws Exception
     */
    void destroy();

}
