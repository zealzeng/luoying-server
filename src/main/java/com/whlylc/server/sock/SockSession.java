package com.whlylc.server.sock;

import com.whlylc.server.ServiceSession;

import java.util.Set;

/**
 * Allow session to write sth to socket connections
 * Created by Zeal on 2018/10/22 0022.
 */
public interface SockSession extends ServiceSession/*<SockConnection>*/ {

        /**
     * One session can have many connections
     * @return
     */
    Set<? extends SockConnection> getConnections();

}
