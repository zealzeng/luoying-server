package com.whlylc.ioc;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A very very rough implementation
 * Created by Zeal on 2018/10/22 0022.
 */
public class ConcurrentApplicationContext implements ApplicationContext {

    protected Map<String,Object> beanMap = new ConcurrentHashMap<>();

    public ConcurrentApplicationContext() {
    }

    public ConcurrentApplicationContext(Map<String,Object> beanMap) {
        this.beanMap.putAll(beanMap);
    }

    /**
     * Add bean to factory
     * @param name
     * @param bean
     */
    public ConcurrentApplicationContext addBean(String name, Object bean) {
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

//    @Override
//    public void destroy() throws Exception {
//        Iterator<Map.Entry<String,Object>> iter = beanMap.entrySet().iterator();
//        while (iter.hasNext()) {
//            Object bean = iter.next().getValue();
//            if (bean != null && (bean instanceof DisposableBean)) {
//                try {
//                    ((DisposableBean) bean).destroy();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
