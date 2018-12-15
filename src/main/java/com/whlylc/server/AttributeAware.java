package com.whlylc.server;

/**
 * Attribute extraction
 * Created by Zeal on 2018/10/22 0022.
 */
public interface AttributeAware {

    /**
     * Set attribute
     * @param name
     * @param o
     */
    void setAttribute(String name, Object o);

    /**
     * Get attribute by name
     * @param name
     * @return
     */
    Object getAttribute(String name);

}
