package com.luoying.server;

import com.luoying.ioc.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class ConcurrentServiceContext implements ServiceContext {

    private Map<String,Object> attrMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext = null;

    public ConcurrentServiceContext() {
    }

    @Override
    public void setAttribute(String name, Object o) {
        attrMap.put(name, o);
    }

    @Override
    public Object getAttribute(String name) {
        return attrMap.get(name);
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
