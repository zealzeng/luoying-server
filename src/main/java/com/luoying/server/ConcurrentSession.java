package com.luoying.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public abstract class ConcurrentSession implements ServiceSession {

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
