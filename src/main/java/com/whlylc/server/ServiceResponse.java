package com.whlylc.server;

import java.nio.charset.Charset;

/**
 * Service response
 * Created by Zeal on 2018/10/21 0021.
 */
public interface ServiceResponse <C extends ServiceConnection> {

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
     * Get connection
     * @return
     */
    C getConnection();

}
