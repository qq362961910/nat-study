package com.jy.study.nat.handler;

import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.exception.SocketClosedException;
import com.jy.study.nat.executor.response.ResponseCommandExecutor;
import com.jy.study.nat.executor.response.ResponseExecutorProvider;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.server.ServerContext;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketHandler implements Runnable {

    private static Logger logger = Logger.getLogger(SocketHandler.class.getName());

    private ServerContext serverContext;
    private ChannelContext channelContext = new ChannelContext();
    private BufferedInputStream in = null;
    private BufferedOutputStream out = null;

    public void run() {
        try {
            LengthMessageReader lengthMessageReader = new LengthMessageReader(in);
            while (true) {
                try {
                    Message message = lengthMessageReader.readMessage();
                    //截取使用的字节数组
                    for (ResponseCommandExecutor executor: ResponseExecutorProvider.SERVER_EXECUTOR_LIST) {
                        executor.execute(channelContext, message);
                    }
                }catch (SocketClosedException e) {
                    serverContext.getClientList().remove(channelContext.getClientRecord());
                    logger.info(String.format("client: %s:%d closed", channelContext.getClientRecord().getHost(), channelContext.getClientRecord().getPort()));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try { in.close(); } catch (IOException e) { e.printStackTrace(); }
            }
            if(out != null) {
                try { out.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    public SocketHandler(ServerContext serverContext, Socket socket) {
        this.serverContext = serverContext;
        try {
            in = new BufferedInputStream(socket.getInputStream());
            out = new BufferedOutputStream(socket.getOutputStream());
            channelContext.setOut(out);
            channelContext.setClientRecord(new ClientRecord(socket.getInetAddress().getHostName(), socket.getPort()));
            channelContext.setServerContext(serverContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
