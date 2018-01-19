package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;

import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ServerContext {

    private List<ClientRecord> clientList;

    public Map<ClientRecord, Socket> clientRecordSocketMap;

    public List<ClientRecord> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientRecord> clientList) {
        this.clientList = clientList;
    }

    public Map<ClientRecord, Socket> getClientRecordSocketMap() {
        return clientRecordSocketMap;
    }

    public void setClientRecordSocketMap(Map<ClientRecord, Socket> clientRecordSocketMap) {
        this.clientRecordSocketMap = clientRecordSocketMap;
    }
}
