# Luoying Server Framework
Luoying server是一个轻量级的服务器开发框架，方便开发者快速的定制多种协议的服务器，servlet style的API和简单的容器注入模式让开发者专注于业务实现。

## Maven地址
```
<dependency>
  <groupId>com.whlylc</groupId>
  <artifactId>luoying-server</artifactId>
  <version>0.2.0-beta2</version>
</dependency>
```

## HTTP服务器
- HttpRequest和HttpResponse与Servlet API接近
- ConcurrentApplicationContext是简单的BeanFactory实现, 方便注入所需组件

参考TestHttpServer.java
```java
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
        HttpServer server = Servers.createHttpServer(service);
        server.startup();
```

## Socket服务器
- socket通信协议简单使用Netty的LengthFieldBasedFrameDecoder，即长度+消息体
```java
        DefaultApplicationContext ctx = ApplicationContexts.createDefaultApplicationContext();
        ctx.addBean("mybatis", new String("mybatisService"));

        SockService service = new SockService(ctx) {
            @Override
            public void service(SockRequest request, SockResponse response) throws Exception {
                System.out.println(request.getRequestBody().toString());
                response.write(String.valueOf(System.currentTimeMillis()));
            }
        };
        //Attach application if it's necessary
        SockServer server = Servers.createSocketServer(9090, service);
        server.startup();
```    

## 简单的IOC支持,支持配置文件, 简单适配spring
```java
        //默认的实现,默认去环境变量APP_CONFIG_DIR,或当前目录,当前config目录查找app.properties
        //其它版本ApplicationContexts.createDefaultApplicationContext也可以手工设置目录或文件名
        //同时ApplicationContext也简单实现了自己的Envvironment
        DefaultApplicationContext appCtx = ApplicationContexts.createDefaultApplicationContext();
        appCtx.addBean("mybatis", new String("mybatisService"));

        HttpServer server = Servers.createHttpServer(new HttpService(appCtx) {
            @Override
            public void service(HttpRequest request, HttpResponse response) throws Exception {
                response.write("Hello" + System.currentTimeMillis());
            }
        });
        server.startup();
```

```java
        //Spring框架的简单适配, Use spring xml or annotated class
        ApplicationContext appCtx = ApplicationContexts.createSpringApplicationContext("c:/application-context.xml");
        HttpServer server = Servers.createHttpServer(new HttpService(appCtx) {
            @Override
            public void service(HttpRequest request, HttpResponse response) throws Exception {
                response.write("Hello" + System.currentTimeMillis());
            }
        });
        server.startup();
```

## 服务器参数
主要在ServerOptions, HttpServerOptions,SockServerOptions中定义

## 开发计划
写着写着越来越像vertx了, 是有些造轮子的做法，相比的优势可能是小巧些,, 方便自己扩展自己的ChannelInitializer, 定制自己的encoder,decoder,
我们自己的物联网项目也基于该框架, 小规模运行暂时还稳定, 毕竟只是简单的基于netty包了一层。 有些场景套接字程序可能简单的用netty好些, 毕竟
往上封装request, response也算是一种损耗。

实际的业务处理都是不推荐在event loop中处理的,  简单用的是JDK自带线程池, 后面看disruptor是否好些。
