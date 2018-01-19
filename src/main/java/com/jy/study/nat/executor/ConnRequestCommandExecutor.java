package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.result.CommandExecuteResult;
import com.jy.study.nat.server.ChannelContext;

import java.util.Scanner;

public class ConnRequestCommandExecutor extends CommandExecutor {

    @Override
    public CommandExecuteResult doExecute(ChannelContext context, Message in) {
        Message msg = new Message();
        msg.setCommand(CommandConstants.conn);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入ip");
        String ip = scanner.nextLine();
        System.out.println("请输入端口号");
        int port = Integer.parseInt(scanner.nextLine().trim());
        msg.setContent((ip + ":" + port));
        CommandExecuteResult result = new CommandExecuteResult();
        result.setSuccess(true);
        result.setMessage(msg);
        return result;
    }

    public ConnRequestCommandExecutor() {
        super(CommandConstants.conn);
    }
}
