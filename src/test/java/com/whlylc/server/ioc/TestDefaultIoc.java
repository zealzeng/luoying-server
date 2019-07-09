package com.whlylc.server.ioc;

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
public class TestDefaultIoc {

    public static void main(String[] args) throws Exception {

        //Default IOC implementation, it contains a DefaultEnvironment specified in file app.properties
        //Other versions of ApplicationContexts.createDefaultApplicationContext support  system environment path, absolute path or relative path to find the property file
        DefaultApplicationContext appCtx = ApplicationContexts.createDefaultApplicationContext();
        appCtx.addBean("mybatis", new String("mybatisService"));

        HttpServer server = Servers.createHttpServer(new HttpService(appCtx) {
            @Override
            public void service(HttpRequest request, HttpResponse response) throws Exception {
                response.write("Hello" + System.currentTimeMillis());
            }
        });
        server.startup();

    }

}
