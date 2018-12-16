package com.whlylc.server.multi;

import com.whlylc.ioc.ConcurrentApplicationContext;
import com.whlylc.server.ChannelService;
import com.whlylc.server.MultiChannelServer;
import com.whlylc.server.http.DefaultHttpChannelService;
import com.whlylc.server.http.HttpRequest;
import com.whlylc.server.http.HttpResponse;
import com.whlylc.server.http.HttpService;
import com.whlylc.server.sock.*;

/**
 * Created by Administrator on 2018/12/16 0016.
 */
public class TestMultiPortServer {

    public static void main(String[] args) throws Exception {

        HttpService httpService = new HttpService() {
            @Override
            public void service(HttpRequest request, HttpResponse response) throws Exception {
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        SockService sockService = new SockService() {
            @Override
            public void service(SockRequest request, SockResponse response) throws Exception {
                System.out.println(request.getRequestBody().toString());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };

        //Attach application if it's necessary
        ConcurrentApplicationContext ctx = new ConcurrentApplicationContext();
        ctx.addBean("mybatis", new String("mybatisService"));
        sockService.setApplicationContext(ctx);
        httpService.setApplicationContext(ctx);

        DefaultHttpChannelService httpChannelService = new DefaultHttpChannelService(8080, httpService);
        DefaultSockChannelService sockChannelService = new DefaultSockChannelService(9090, sockService);
        ChannelService[] services = new ChannelService[] {httpChannelService, sockChannelService};
        MultiChannelServer server = new MultiChannelServer(services);
        server.startup();

    }


}
