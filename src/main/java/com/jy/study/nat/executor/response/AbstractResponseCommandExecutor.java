package com.jy.study.nat.executor.response;

import com.jy.study.nat.entity.Message;
import com.jy.study.nat.server.ChannelContext;

import java.io.IOException;

public abstract class AbstractResponseCommandExecutor implements ResponseCommandExecutor {

    protected String command;

    public void execute(ChannelContext context, Message message) throws IOException {
        if(command.equals(message.getCommand())) {
            doExecute(context, message);
        }
    }

    public abstract void doExecute(ChannelContext context, Message message) throws IOException;

    public AbstractResponseCommandExecutor(String command) {
        this.command = command;
    }

}
