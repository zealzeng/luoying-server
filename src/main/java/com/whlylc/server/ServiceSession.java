package com.whlylc.server;

/**
 * Application session
 * Created by Zeal on 2018/10/22 0022.
 */
public interface ServiceSession extends AttributeAware {

    /**
     * Close session
     */
    void invalidate();

}
