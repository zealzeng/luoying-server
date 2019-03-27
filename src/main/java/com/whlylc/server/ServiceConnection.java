package com.whlylc.server;

import java.nio.charset.Charset;

/**
 * Multiple requests can use one connection, and connection is writable
 * Actually it's the wrapper for Channel class in netty, write and close will affect the whole pipeline
 * Created by Zeal on 2019/3/24 0024.
 */
public interface ServiceConnection<C extends ServiceConnection> {

    /**
     * Write bytes
     * @param bytes
     */
    ConnectionFuture<C> write(byte[] bytes);

    /**
     * Default charset is UTF-8
     * @param cs
     */
    ConnectionFuture<C> write(CharSequence cs);

    /**
     * @param cs
     */
    ConnectionFuture<C> write(CharSequence cs, Charset charset);

    /**
     * Like servlet context
     * @return
     */
    ServiceContext getServiceContext();

    /**
     * Close connection
     */
    void close();

    /**
     * It can be null
     * @return
     */
    ServiceSession getSession();

//    /**
//     * Fill session
//     * @param session
//     */
//    void setSession(ServiceSession session);
}
