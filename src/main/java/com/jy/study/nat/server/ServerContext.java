package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;

import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * server context
 * */
public class ServerContext {

    /**
     * 客户端记录集合
     * */
    private List<ClientRecord> clientList;

    /**
     * 客户端记录套接子映射
     * */
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
