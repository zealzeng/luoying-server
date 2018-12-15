package com.whlylc.server.sock;

import com.whlylc.server.ServiceResponse;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public interface SockResponse extends ServiceResponse {

    void write(byte[] bytes);

    void write(CharSequence cs);

}
