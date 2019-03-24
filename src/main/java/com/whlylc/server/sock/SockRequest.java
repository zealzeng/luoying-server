package com.whlylc.server.sock;

import com.whlylc.server.ServiceConnection;
import com.whlylc.server.ServiceRequest;
import io.netty.buffer.ByteBuf;

import java.net.SocketAddress;

/**
 * Socket request, request is not thread safe
 * Created by Zeal on 2018/10/21 0021.
 */
public interface SockRequest extends ServiceRequest {

    /**
     * Get request body
     * @return
     */
    ByteBuf getRequestBody();

    /**
     * Get socket session
     * @param create true - create session is it's not present
     * @return
     */
    SockSession getSession(boolean create);

    /**
     * Get local address
     * @return
     */
    SocketAddress getLocalAddr();

    /**
     * Get remote address
     * @return
     */
    SocketAddress getRemoteAddr();

    /**
     * Get connection
     * @return
     */
    SockConnection getConnection();

}
