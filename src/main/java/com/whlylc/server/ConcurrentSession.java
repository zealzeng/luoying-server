package com.whlylc.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zeal on 2018/10/22 0022.
 * @deprecated
 */
public abstract class ConcurrentSession/*<C extends ServiceConnection>*/ implements ServiceSession/*<C>*/ {

    private Map<String,Object> attrMap = new ConcurrentHashMap<>();

    @Override
    public void setAttribute(String name, Object o) {
        attrMap.put(name, o);
    }

    @Override
    public Object getAttribute(String name) {
        return attrMap.get(name);
    }

}
