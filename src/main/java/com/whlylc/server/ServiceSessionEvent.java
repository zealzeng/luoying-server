package com.whlylc.server;

import java.util.EventObject;

/**
 * Created by Zeal on 2019/3/22 0022.
 */
public class ServiceSessionEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ServiceSessionEvent(ServiceSession source) {
        super(source);
    }

    /**
     * Get service session
     * @return
     */
    public ServiceSession getSession() {
        return (ServiceSession) super.getSource();
    }
}
