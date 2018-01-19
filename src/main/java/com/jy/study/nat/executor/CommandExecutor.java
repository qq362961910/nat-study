package com.jy.study.nat.executor;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.executor.result.CommandExecuteResult;
import com.jy.study.nat.server.ChannelContext;

public abstract class CommandExecutor implements Executor {

    protected String command;

    public CommandExecuteResult execute(ChannelContext context, Message message) {
        if(command.equals(message.getCommand())) {
            return doExecute(context, message);
        }
        return null;
    }

    public abstract CommandExecuteResult doExecute(ChannelContext context, Message message);

    public CommandExecutor(String command) {
        this.command = command;
    }

}
