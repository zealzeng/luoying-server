package com.whlylc.server.http;

import com.whlylc.server.ServiceSession;

import java.util.Set;

/**
 * Created by Zeal on 2018/10/22 0022.
 */
public interface HttpSession extends ServiceSession {

    /**
     * One session can have many connections
     * @return
     */
    Set<HttpConnection> getConnections();


}
