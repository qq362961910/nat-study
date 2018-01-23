package com.jy.study.nat.client;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.executor.request.RequestCommandExecutor;
import com.jy.study.nat.executor.request.RequestExecutorProvider;
import com.jy.study.nat.listener.InputStreamListener;
import com.jy.study.nat.server.ChannelContext;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {


    private static Logger logger = Logger.getLogger(Client.class.getName());

    private static String host = "192.168.115.109";
    private static int port = 5000;
    private static ChannelContext channelContext = new ChannelContext();

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.setReuseAddress(true);
        socket.connect(new InetSocketAddress(host, port));
        int localPort = socket.getLocalPort();
        channelContext.setBindPort(localPort);
        logger.info(String.format("本地端口号: %s", localPort));
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        channelContext.setOut(out);
        new Thread(new InputStreamListener(in, localPort)).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showCommands();
            String command = scanner.nextLine().trim().replace("\r\n", "");
            writeRequest(command);
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

    public static void writeRequest(String command) throws IOException {
        for (RequestCommandExecutor executor: RequestExecutorProvider.CLIENT_EXECUTOR_LIST) {
            executor.execute(channelContext, command);
        }
    }
}

