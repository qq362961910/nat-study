package com.jy.study.nat.handler;

import com.jy.study.nat.entity.ClientRecord;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.Executor;
import com.jy.study.nat.executor.ExecutorProvider;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.server.ServerContext;
import com.jy.study.nat.util.MessageUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class SocketHandler implements Runnable {
    private ServerContext serverContext;
    private ChannelContext channelContext = new ChannelContext();
    private BufferedInputStream in = null;
    private BufferedOutputStream out = null;
    private int msgLength = -1;
    private byte[] buff1 = new byte[8192];
    private byte[] buff2 = new byte[8192];
    private int index = 0;
    private boolean isFirst = true;

    public void run() {
        try {
            byte[] bytes = new byte[1024];
            int len;
            while (true) {
                //读取长度
                if(msgLength == -1) {
                    msgLength = in.read();
                    if(msgLength == -1) {
                        System.out.println(String.format("client: %s:%d closed", channelContext.getClientRecord().getHost(), channelContext.getClientRecord().getPort()));
                        break;
                    }
                }
                len = in.read(bytes);
                if(len > -1) {
                    byte[] currentBuff = getCurrentBuff();
                    System.arraycopy(bytes, 0, currentBuff, index, len);
                    index+=len;
                    //消息完整
                    if(index>=msgLength) {
                        //截取使用的字节数组
                        Message in = MessageUtil.translate(Arrays.copyOfRange(currentBuff, 0, msgLength));
                        for (Executor executor: ExecutorProvider.SERVER_EXECUTOR_LIST) {
                            executor.execute(channelContext, in);
                        }
                        int copyLength = index - msgLength;
                        byte[] otherBuff = getOtherBuff();
                        System.arraycopy(currentBuff, msgLength, otherBuff, 0, copyLength);
                        index = copyLength;
                        changBuff();
                        msgLength = -1;
                    } else {
                        //消息不完整,记录数据缓存下标
                        index = len;
                    }
                }
                else {
                    serverContext.getClientList().remove(channelContext.getClientRecord());
                    System.out.println(String.format("client: %s:%d closed", channelContext.getClientRecord().getHost(), channelContext.getClientRecord().getPort()));
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

    public byte[] getCurrentBuff() {
        return isFirst ? buff1 : buff2;
    }
    public byte[] getOtherBuff() {
        return isFirst ? buff2 : buff1;
    }
    public void changBuff() {
        if(isFirst) {
            isFirst = false;
        } else {
            isFirst = true;
        }
    }
}
