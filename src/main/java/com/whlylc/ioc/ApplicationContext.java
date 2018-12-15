package com.whlylc.ioc;

/**
 * Application context, business bean factory, only support singleton bean
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ApplicationContext {

    /**
     * @param requiredType
     * @param <T>
     * @return null if it's not existed, should throw exception if duplicate type in factory
     */
    <T> T getBean(Class<T> requiredType);

    /**
     * @param name
     * @return null if it's not existed
     */
    Object getBean(String name);

    /**
     * Get all bean names
     * @return
     */
    String[] getBeanNames();

}
