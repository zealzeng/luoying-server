package com.whlylc.server.http;

import com.whlylc.server.ConcurrentSession;

import java.util.Set;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public class DefaultHttpSession implements HttpSession {

    /**
     * Close session
     */
    public void invalidate() {
        //TODO
    }

    @Override
    public Set<HttpConnection> getConnections() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }
}
