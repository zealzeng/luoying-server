package com.whlylc.server.http;

import com.whlylc.ioc.DefaultApplicationContext;
import com.whlylc.ioc.utils.ApplicationContexts;
import com.whlylc.server.util.Servers;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class TestHttpServer {


    public static void main(String[] args) throws Exception {

        //If we need business beans, inject here
        DefaultApplicationContext appCtx = ApplicationContexts.createDefaultApplicationContext();
        appCtx.addBean("mybatis", new String("mybatisService"));

        HttpService service = new HttpService(appCtx) {
            @Override
            public void service(HttpRequest request, HttpResponse response) {
                System.out.println(request.getRequestPath());
                response.write(request.getScheme());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        HttpServer server = Servers.createHttpServer(8080, service);
        server.startup();
    }

}
