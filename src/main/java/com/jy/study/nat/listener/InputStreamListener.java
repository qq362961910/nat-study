package com.jy.study.nat.listener;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.exception.SocketClosedException;
import com.jy.study.nat.handler.LengthMessageReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InputStreamListener implements Runnable{

    private BufferedInputStream in;
    private int localPort;

    public InputStreamListener(BufferedInputStream in, int localPort) {
        this.in = in;
        this.localPort = localPort;
    }

    @Override
    public void run() {
        LengthMessageReader lengthMessageReader = new LengthMessageReader(in);
        try {
            while (true) {
                try {
                    Message message = lengthMessageReader.readMessage();
                    //conn
                    if(CommandConstants.conn.equals(message.getCommand())) {
                        String[] entry = message.getContent().split(":");
                        String targetIp = entry[0];
                        int targetPort = Integer.parseInt(entry[1]);
                        System.out.println(String.format("receive a conn command, targetIp: %s, targetPort: %s", targetIp, targetPort) +", connecting....");
                        Socket socket = new Socket();
                        socket.setReuseAddress(true);
                        socket.bind(new InetSocketAddress(
                                InetAddress.getLocalHost().getHostAddress(), localPort));
                        socket.connect(new InetSocketAddress(targetIp, targetPort));
                        System.out.println("connection established");
                    } else {
                        System.out.println(String.format("receive a message: \r\n%s", message));
                    }
                } catch (SocketClosedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try { in.close(); } catch (IOException e) {e.printStackTrace();}
            }
        }
        System.out.println("input stream listener exit");
    }
}
