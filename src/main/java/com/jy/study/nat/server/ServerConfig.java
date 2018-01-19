package com.jy.study.nat.server;

public class ServerConfig {

    /**
     * bind port
     * */
    private int bindPort = 5000;

    /**
     * thread pool size
     * */
    private int threadPoolSize = 10;

    public int getBindPort() {
        return bindPort;
    }

    public void setBindPort(int bindPort) {
        this.bindPort = bindPort;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
