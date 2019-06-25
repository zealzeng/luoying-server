package com.whlylc.server.sock;

import com.whlylc.ioc.DefaultApplicationContext;

/**
 * Created by Administrator on 2018/10/21 0021.
 */
public class TestSocketServer {

    public static void main(String[] args) throws Exception {

        DefaultApplicationContext ctx = new DefaultApplicationContext();
        ctx.addBean("mybatis", new String("mybatisService"));

        SockService service = new SockService(ctx) {
            @Override
            public void service(SockRequest request, SockResponse response) throws Exception {
                System.out.println(request.getRequestBody().toString());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        //Attach application if it's necessary
        DefaultSockServer server = new DefaultSockServer(9090, service);
        server.startup();

    }

}
