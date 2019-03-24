package com.whlylc.server;

/**
 * Multiple requests can use one connection, and we allow connection to write.
 * Since we will create brand new request/response object for each request, so never save request/response object in connection instance.
 * Created by Zeal on 2019/3/24 0024.
 */
public interface ServiceConnection extends ServiceResponse {

    /**
     * Like servlet context
     * @return
     */
    ServiceContext getServiceContext();

    /**
     * Close connection
     */
    void close();
}
