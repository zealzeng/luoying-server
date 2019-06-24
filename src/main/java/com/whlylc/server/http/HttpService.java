package com.whlylc.server.http;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.server.ServiceRequest;
import com.whlylc.server.ServiceResponse;
import com.whlylc.server.GenericService;

/**
 * Http service extraction
 * Created by Zeal on 2018/9/16 0016.
 */
public abstract class HttpService extends GenericService<HttpConnection,HttpRequest,HttpResponse> {

    public HttpService() {
    }

    public HttpService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
