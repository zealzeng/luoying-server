package com.whlylc.server.sock;
import com.whlylc.ioc.ApplicationContext;
import com.whlylc.server.GenericService;

/**
 * Socket service extraction
 * Created by Zeal on 2018/10/21 0021.
 */
public abstract class SockService extends GenericService<SockConnection,SockRequest,SockResponse> {

    public SockService() {
    }

    public SockService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


}
