package com.whlylc.server.util;

import com.whlylc.server.http.DefaultHttpServer;
import com.whlylc.server.http.HttpServer;
import com.whlylc.server.http.HttpServerOptions;
import com.whlylc.server.http.HttpService;
import com.whlylc.server.sock.DefaultSockServer;
import com.whlylc.server.sock.SockServer;
import com.whlylc.server.sock.SockServerOptions;
import com.whlylc.server.sock.SockService;

/**
 * Utility to create server
 */
public class Servers {

    public static HttpServer createHttpServer(int port, HttpService httpService) {
        return new DefaultHttpServer(port, httpService);
    }

    public static HttpServer createHttpServer(HttpServerOptions serverOptions, HttpService httpService) {
        return new DefaultHttpServer(serverOptions, httpService);
    }

    public static SockServer createSocketServer(int port, SockService sockService) {
        return new DefaultSockServer(port, sockService);
    }

    public static SockServer createSocketServer(SockServerOptions serverOptions, SockService sockService) {
        return new DefaultSockServer(serverOptions, sockService);
    }

}
