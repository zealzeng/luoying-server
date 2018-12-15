package com.whlylc.server.http;

import com.whlylc.server.ServiceRequest;
import com.whlylc.server.ServiceResponse;
import com.whlylc.server.GenericService;

/**
 * Http service extraction
 * Created by Zeal on 2018/9/16 0016.
 */
public abstract class HttpService extends GenericService {

    public void service(ServiceRequest request, ServiceResponse response) throws Exception {
        if (!(request instanceof HttpRequest) && (response instanceof HttpResponse)) {
            throw new IllegalStateException("Not http request or response");
        }
        HttpRequest req = (HttpRequest) request;
        HttpResponse resp = (HttpResponse) response;
        service(req, resp);
    }

    /**
     * Http service with http request and response
     * @param request
     * @param response
     * @throws Exception
     */
    public abstract void service(HttpRequest request, HttpResponse response) throws Exception;

}
