package com.jy.study.nat.executor.request;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;
import com.jy.study.nat.util.MessageUtil;

import java.io.IOException;

public class ListClientsRequestCommandExecutor extends AbstractRequestCommandExecutor {

    @Override
    public void doExecute(ChannelContext context) throws IOException {
        Message msg = new Message();
        msg.setCommand(CommandConstants.list);
        MessageUtil.writeMessage(context.getOut(), msg);
    }

    public ListClientsRequestCommandExecutor() {
        super(CommandConstants.list);
    }
}
