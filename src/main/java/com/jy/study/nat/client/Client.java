package com.jy.study.nat.client;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.ConnRequestCommandExecutor;
import com.jy.study.nat.executor.ListClientsRequestCommandExecutor;
import com.jy.study.nat.listener.InputStreamListener;
import com.jy.study.nat.util.MessageUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static String host = "192.168.115.109";
    private static int port = 5000;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.connect(new InetSocketAddress(host, port));
        int localPort = socket.getLocalPort();
        System.out.println(String.format("本地端口号: %s", localPort));
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        new Thread(new InputStreamListener(in, localPort)).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showCommands();
            String command = scanner.nextLine().trim().replace("\r\n", "");
            Message message = buildMessage(command);
            byte[] content = MessageUtil.translate(message);
            out.write(content.length);
            out.write(content);
            out.flush();
            if(command.equals(CommandConstants.conn)) {
                String[] entry = message.getContent().split(":");
                String targetIp = entry[0];
                int targetPort = Integer.parseInt(entry[1]);
                Socket newSocket = new Socket();
                newSocket.setReuseAddress(true);
                newSocket.bind(new InetSocketAddress(
                        InetAddress.getLocalHost().getHostAddress(), localPort));
                newSocket.connect(new InetSocketAddress(targetIp, targetPort));
            }
            System.out.println("write a message, length: " + content.length);
            System.out.println("type any key to continue");
            scanner.nextLine();
        }
    }
    public static void showCommands() {
        System.out.println("all commands available");
        int index = 1;
        for (String c: CommandConstants.commandList) {
            System.out.println(String.format("command.%d:  %s", index++, c));
        }
    }

    public static Message buildMessage(String command) {
        Message c = new Message();
        c.setCommand(command);
        if(CommandConstants.list.equals(command)) {
            return new ListClientsRequestCommandExecutor().execute(null, c).getMessage();
        } else if(CommandConstants.conn.equals(command)) {
            return new ConnRequestCommandExecutor().execute(null, c).getMessage();
        } else {
            return null;
        }

    }

}

