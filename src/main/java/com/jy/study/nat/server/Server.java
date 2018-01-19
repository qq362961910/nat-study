package com.jy.study.nat.server;

import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.handler.SocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server implements Runnable{

    private static Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws Exception {
        ServerConfig config = new ServerConfig();
        new Thread(new Server(config)).start();
    }

    /**
     * 运行状态
     * */
    private volatile boolean stop = false;
    /**
     * 客户端列表
     * */
    private final List<ClientRecord> clientList = new ArrayList<>();
    /**
     * 客户端套接字映射
     * */
    public final Map<ClientRecord, Socket> clientRecordSocketMap = new HashMap<>();

    /**
     * 服务器配置
     * */
    private ServerConfig serverConfig = new ServerConfig();

    /**
     * server context
     * */
    private ServerContext serverContext = new ServerContext();


    @Override
    public void run() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(serverConfig.getThreadPoolSize());
            ServerSocket serverSocket = new ServerSocket(serverConfig.getBindPort());
            logger.info(String.format("server start at port: %d", serverConfig.getBindPort()));
            while (true) {
                if(stop) {
                    logger.info(String.format("server stops ap port: %d", serverConfig.getBindPort()));
                    break;
                }
                Socket client = serverSocket.accept();
                acceptClient(executorService, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptClient(ExecutorService executorService, Socket client) {
        InetSocketAddress clientAddress = (InetSocketAddress)client.getRemoteSocketAddress();
        ClientRecord clientRecord = new ClientRecord(clientAddress.getHostName(), clientAddress.getPort());
        clientList.add(clientRecord);
        logger.info(String.format("accept new client, address: %s:%d", clientAddress.getHostName(), clientAddress.getPort()));
        clientRecordSocketMap.put(clientRecord, client);
        executorService.submit(new SocketHandler(serverContext, client));
    }

    private Server(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        serverContext.setClientList(clientList);
        serverContext.setClientRecordSocketMap(clientRecordSocketMap);
    }

    public void stopServer() {
        stop = true;
    }

    public List<ClientRecord> getClientList() {
        return Collections.unmodifiableList(clientList);
    }
}
