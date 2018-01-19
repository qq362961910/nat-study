package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;

import java.io.BufferedOutputStream;

public class ChannelContext {

    private BufferedOutputStream out;

    private ClientRecord clientRecord;

    private ServerContext serverContext;

    private int bindPort;

    public BufferedOutputStream getOut() {
        return out;
    }

    public void setOut(BufferedOutputStream out) {
        this.out = out;
    }

    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    public void setClientRecord(ClientRecord clientRecord) {
        this.clientRecord = clientRecord;
    }

    public ServerContext getServerContext() {
        return serverContext;
    }

    public void setServerContext(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    public int getBindPort() {
        return bindPort;
    }

    public void setBindPort(int bindPort) {
        this.bindPort = bindPort;
    }
}
