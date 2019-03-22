package com.whlylc.server.sock;

import com.whlylc.server.ServiceResponse;

import java.nio.charset.Charset;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public interface SockResponse extends ServiceResponse {

    int DO_NOTHING = 0;

    int CLOSE = 1;

    int CLOSE_ON_FAILURE = 2;

    int FIRE_EXCEPTION_ON_FAILURE = 3;

    /**
     *
     * @param bytes
     * @param futureAction DO_NOTHING, CLOSE, CLOSE_ON_FAILURE, FIRE_EXCEPTION_ON_FAILURE
     */
    void write(byte[] bytes, int futureAction);

    /**
     * @param cs
     * @param futureAction DO_NOTHING, CLOSE, CLOSE_ON_FAILURE, FIRE_EXCEPTION_ON_FAILURE
     */
    void write(CharSequence cs, int futureAction);

    /**
     * @param cs
     * @param charset
     * @param futureAction DO_NOTHING, CLOSE, CLOSE_ON_FAILURE, FIRE_EXCEPTION_ON_FAILURE
     */
    void write(CharSequence cs, Charset charset, int futureAction);

}
