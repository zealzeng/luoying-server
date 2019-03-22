package com.whlylc.server;

import java.util.EventListener;

/**
 * Created by Zeal on 2019/3/22 0022.
 */
public interface ServiceSessionListener extends EventListener {

    /**
     * Receives notification that a session has been created.
     */
    void sessionCreated(ServiceSessionEvent se);

    /**
     * Receives notification that a session is about to be invalidated.
     */
    void sessionDestroyed(ServiceSessionEvent se);

}
