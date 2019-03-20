package com.whlylc.server.sock;

import com.whlylc.server.ServiceSession;

/**
 * Long persistent connection, allow session to write
 * Created by Zeal on 2018/10/22 0022.
 */
public interface SockSession extends ServiceSession,SockResponse {

//    /**
//     * Write byte to channel
//     * @param bytes
//     */
//    void write(byte[] bytes);
//
//    /**
//     * write string to channel
//     * @param cs
//     */
//    void write(CharSequence cs);

}
