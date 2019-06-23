package com.whlylc.server;

import java.util.concurrent.Executor;

/**
 * Server context
 */
public interface ServerContext {

    <T extends ServerOptions>T getServerOptions();

    Executor getWorkerPoolExecutor();
}
