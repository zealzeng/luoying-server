package com.whlylc.server.sock;

import com.whlylc.server.ServiceRequest;
import io.netty.buffer.ByteBuf;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public interface SockRequest extends ServiceRequest {

    ByteBuf getRequestBody();

    SockSession getSession(boolean create);

}
