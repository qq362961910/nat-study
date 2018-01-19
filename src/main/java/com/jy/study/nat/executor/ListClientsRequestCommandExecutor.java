package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;

public class ListClientsRequestCommandExecutor extends CommandExecutor {

    @Override
    public void doExecute(ChannelContext context, Message in) throws IOException {
        Message msg = new Message();
        msg.setCommand(CommandConstants.list);
        writeMessage(context.getOut(), msg);
    }

    public ListClientsRequestCommandExecutor() {
        super(CommandConstants.list);
    }
}
