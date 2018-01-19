package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.handler.SocketHandler;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int port = 5000;
    public static final List<ClientRecord> clientList = new ArrayList<>();
    public static final Map<ClientRecord, Socket> clientRecordSocketMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println(String.format("server start at port: %d", port));
        while (true) {
            Socket client = serverSocket.accept();
            InetSocketAddress socketAddress = (InetSocketAddress)client.getRemoteSocketAddress();
            System.out.println(String.format("accept new client, address: %s:%d", socketAddress.getHostName(), socketAddress.getPort()));
            ClientRecord clientRecord = new ClientRecord(socketAddress.getHostName(), socketAddress.getPort());
            clientList.add(clientRecord);
            clientRecordSocketMap.put(clientRecord, client);
            executorService.submit(new SocketHandler(client, clientRecord));
        }
    }

}
