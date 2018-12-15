package com.whlylc.server.sock;

import com.whlylc.ioc.ConcurrentApplicationContext;

/**
 * Created by Administrator on 2018/10/21 0021.
 */
public class TestSocketServer {

    public static void main(String[] args) throws Exception {

        SockService service = new SockService() {
            @Override
            public void service(SockRequest request, SockResponse response) throws Exception {
                System.out.println(request.getRequestBody().toString());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        //Attach application if it's necessary
        ConcurrentApplicationContext ctx = new ConcurrentApplicationContext();
        ctx.addBean("mybatis", new String("mybatisService"));
        service.setApplicationContext(ctx);


        DefaultSockServer server = new DefaultSockServer(9090, service);
        server.startup();

    }

}
