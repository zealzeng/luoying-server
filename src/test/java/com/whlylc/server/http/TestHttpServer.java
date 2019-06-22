package com.whlylc.server.http;

import com.whlylc.ioc.ConcurrentApplicationContext;

/**
 * Created by Zeal on 2018/10/21 0021.
 */
public class TestHttpServer {


    public static void main(String[] args) throws Exception {

        HttpService service = new HttpService() {
            @Override
            public void service(HttpRequest request, HttpResponse response) {
                System.out.println(request.getRequestPath());
                response.write(request.getScheme());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        //If we need business beans, inject here
        ConcurrentApplicationContext appCtx = new ConcurrentApplicationContext();
        appCtx.addBean("mybatis", new String("mybatisService"));
        service.setApplicationContext(appCtx);

        DefaultHttpServer server = new DefaultHttpServer(8080, service);
        server.startup();
    }

}
