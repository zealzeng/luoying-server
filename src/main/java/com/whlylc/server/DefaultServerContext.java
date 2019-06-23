package com.whlylc.server;

import io.netty.util.NettyRuntime;

import java.util.concurrent.*;

/**
 * @author Zeal
 */
public class DefaultServerContext implements ServerContext {

    protected ServerOptions serverOptions = null;

    protected Executor workerPoolExecutor = null;

    public DefaultServerContext(ServerOptions serverOptions) {
        this.serverOptions = serverOptions;
        this.initWorkerPoolExecutor();
    }

    private void initWorkerPoolExecutor() {
        int coreSize = NettyRuntime.availableProcessors() * 2;
        int maxWorkers = serverOptions.getMaxWorkerPoolThreads();
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        //FIXME Capacity is unlimited, while there are too task
        int maxWorkerPoolQueueSize = serverOptions.getMaxWorkerPoolQueueSize();
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(maxWorkerPoolQueueSize);
        this.workerPoolExecutor = new ThreadPoolExecutor(coreSize, maxWorkers, keepAliveTime, unit, workQueue, new ThreadPoolExecutor.AbortPolicy());
    }

    public <T extends ServerOptions>T getServerOptions() {
        return (T) this.serverOptions;
    }

    public Executor getWorkerPoolExecutor() {
        return this.workerPoolExecutor;
    }

}
