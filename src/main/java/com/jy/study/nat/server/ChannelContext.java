package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;

import java.io.BufferedOutputStream;

public class ChannelContext {

    private BufferedOutputStream out;

    private ClientRecord clientRecord;

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
}
