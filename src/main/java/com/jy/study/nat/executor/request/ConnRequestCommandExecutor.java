package com.jy.study.nat.executor.request;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.util.MessageUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConnRequestCommandExecutor extends AbstractRequestCommandExecutor {

    @Override
    public void doExecute(ChannelContext context) throws IOException {
        Message msg = new Message();
        msg.setCommand(CommandConstants.conn);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入ip");
        String ip = scanner.nextLine();
        System.out.println("请输入端口号");
        int port = Integer.parseInt(scanner.nextLine().trim());
        msg.setContent((ip + ":" + port));
        MessageUtil.writeMessage(context.getOut(), msg);

        Socket newSocket = new Socket();
        newSocket.setReuseAddress(true);
        newSocket.bind(new InetSocketAddress(
                InetAddress.getLocalHost().getHostAddress(), context.getBindPort()));
        newSocket.connect(new InetSocketAddress(ip, port));
    }

    public ConnRequestCommandExecutor() {
        super(CommandConstants.conn);
    }
}
