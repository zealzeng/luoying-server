package com.whlylc.server.sock;

import com.whlylc.server.ConnectionFuture;
import com.whlylc.server.ServiceConnection;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2019/3/24 0024.
 */
public interface SockConnection extends ServiceConnection {

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
     * It can be null
     * @return
     */
    SockSession getSession();

    /**
     * Fill session
     * @param session
     */
    void setSession(SockSession session);

}
