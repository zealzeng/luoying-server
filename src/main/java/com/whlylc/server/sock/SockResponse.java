package com.whlylc.server.sock;

import com.whlylc.server.ConnectionFuture;
import com.whlylc.server.ServiceResponse;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public interface SockResponse extends ServiceResponse {

        /**
     * Write bytes
     * @param bytes
     */
    ConnectionFuture<SockConnection> write(byte[] bytes);

    /**
     * Default charset is UTF-8
     * @param cs
     */
    ConnectionFuture<SockConnection> write(CharSequence cs);

    /**
     * @param cs
     */
    ConnectionFuture<SockConnection> write(CharSequence cs, Charset charset);

    /**
     * Get connection
     * @return
     */
    SockConnection getConnection();

}
