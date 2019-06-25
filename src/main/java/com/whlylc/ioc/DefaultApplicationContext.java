package com.whlylc.ioc;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A very very rough implementation
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultApplicationContext implements ApplicationContext {

    protected Map<String,Object> beanMap = new ConcurrentHashMap<>();

    protected Environment environment = null;

    public DefaultApplicationContext() {
        this(new DefaultEnvironment());
    }

    public DefaultApplicationContext(Environment environment) {
        this.environment = environment;
        this.addShutdownHook();
    }

    /**
     * Add bean to factory
     * @param name
     * @param bean
     */
    public DefaultApplicationContext addBean(String name, Object bean) {
        this.beanMap.put(name, bean);
        return this;
    }

    /**
     * @param requiredType
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public <T> T getBean(Class<T> requiredType) {
        //A rough implementation
        Iterator<Map.Entry<String,Object>> iter = beanMap.entrySet().iterator();
        while (iter.hasNext()) {
            Object bean = iter.next().getValue();
            if (bean.getClass() == requiredType) {
                return (T) bean;
            }
        }
        return null;
    }

    @Override
    public Object getBean(String name) {
        return beanMap.get(name);
    }

    @Override
    public String[] getBeanNames() {
        Set<String> keySet = this.beanMap.keySet();
        String[] keys = new String[keySet.size()];
        keySet.toArray(keys);
        return keys;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DefaultApplicationContext.this.destroy();
            }
        });
    }

    private void destroy() {
        Iterator<Map.Entry<String,Object>> iter = beanMap.entrySet().iterator();
        while (iter.hasNext()) {
            Object bean = iter.next().getValue();
            if (bean != null && (bean instanceof DisposableBean)) {
                try {
                    ((DisposableBean) bean).destroy();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
