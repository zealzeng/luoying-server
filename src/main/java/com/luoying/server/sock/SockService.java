package com.luoying.server.sock;

import com.luoying.server.ServiceRequest;
import com.luoying.server.ServiceResponse;
import com.luoying.server.GenericService;

/**
 * Socket service extraction
 * Created by Zeal on 2018/10/21 0021.
 */
public abstract  class SockService extends GenericService {

    @Override
    public void service(ServiceRequest request, ServiceResponse response) throws Exception {
        if (!(request instanceof SockRequest) && (response instanceof SockResponse)) {
            throw new IllegalStateException("Not socket request or response");
        }
        SockRequest req = (SockRequest) request;
        SockResponse resp = (SockResponse) response;
        service(req, resp);
    }

    /**
     *  Socket service with  socket request and response
     * @param request
     * @param response
     * @throws Exception
     */
    public abstract void service(SockRequest request, SockResponse response) throws Exception;
}
