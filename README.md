# Luoying Server Framework
Luoying server是一个轻量级的服务器开发框架，方便开发者快速的定制多种协议的服务器，servlet style的API和简单的容器注入模式让开发者专注于业务实现。

# HTTP服务器
- HttpRequest和HttpResponse与Servlet API接近
- ConcurrentApplicationContext是简单的BeanFactory实现, 方便注入所需组件

参考TestHttpServer.java
```java
    public static void main(String[] args) throws Exception {

        HttpService service = new HttpService() {
            @Override
            public void service(HttpRequest request, HttpResponse response) {
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
```

# Socket服务器
- socket通信协议简单使用Netty的LengthFieldBasedFrameDecoder，即长度+消息体
```java
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
```    

# 多端口服务器
