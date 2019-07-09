package com.whlylc.server.ioc;

import com.whlylc.ioc.ApplicationContext;
import com.whlylc.ioc.DefaultApplicationContext;
import com.whlylc.ioc.utils.ApplicationContexts;
import com.whlylc.server.http.HttpRequest;
import com.whlylc.server.http.HttpResponse;
import com.whlylc.server.http.HttpServer;
import com.whlylc.server.http.HttpService;
import com.whlylc.server.util.Servers;

/**
 * @author Zeal
 */
public class TestSpringIoc {

    public static void main(String[] args) throws Exception {

        //Use spring xml or annotated class
        ApplicationContext appCtx = ApplicationContexts.createSpringApplicationContext("c:/application-context.xml");
        HttpServer server = Servers.createHttpServer(new HttpService(appCtx) {
            @Override
            public void service(HttpRequest request, HttpResponse response) throws Exception {
                response.write("Hello" + System.currentTimeMillis());
            }
        });
        server.startup();
    }

}
