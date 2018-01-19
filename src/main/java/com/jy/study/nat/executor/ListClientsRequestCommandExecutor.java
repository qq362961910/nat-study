package com.jy.study.nat.executor;

import com.jy.study.nat.constants.CommandConstants;
import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.result.CommandExecuteResult;
import com.jy.study.nat.server.ChannelContext;

public class ListClientsRequestCommandExecutor extends CommandExecutor {

    @Override
    public CommandExecuteResult doExecute(ChannelContext context, Message in) {
        CommandExecuteResult result = new CommandExecuteResult();
        result.setSuccess(true);
        Message msg = new Message();
        msg.setCommand(CommandConstants.list);
        result.setMessage(msg);
        return result;
    }

    public ListClientsRequestCommandExecutor() {
        super(CommandConstants.list);
    }
}
