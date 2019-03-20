package com.whlylc.server;

import java.nio.charset.Charset;

/**
 * Service response
 * Created by Zeal on 2018/10/21 0021.
 */
public interface ServiceResponse {

        /**
     * Write bytes
     * @param bytes
     */
    void write(byte[] bytes);

    /**
     * Default charset is UTF-8
     * @param cs
     */
    void write(CharSequence cs);

    /**
     * @param cs
     */
    void write(CharSequence cs, Charset charset);

}
