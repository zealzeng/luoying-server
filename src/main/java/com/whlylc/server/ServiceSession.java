package com.whlylc.server;

import java.util.Set;

/**
 * One session from same user can have several connections
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ServiceSession<C extends ServiceConnection> extends AttributeAware {

    /**
     * Close and invalidate session
     */
    void invalidate();

    /**
     * One session can have many connections
     * @return
     */
    Set<C> getConnections();

}
